package connect.four.bot;

import java.io.ByteArrayOutputStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.PersistBasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.Propagation;
import org.encog.neural.networks.training.propagation.back.Backpropagation;

import connect.four.core.GameProperties;

public class ConnectFourNeuralNetwork {
	private BasicNetwork network;
	private List<Entry<ConnectFourNeuralNetworkInputData, ConnectFourNeuralNetworkOutputData>> trainingData;
	private double targetErrorRate;
	private int maxEpochs;

	public ConnectFourNeuralNetwork() {
		trainingData = new ArrayList<>();
		targetErrorRate = 0.01;
		maxEpochs = 500;
		createNeuralNetwork();
	}

	public void addTrainingData(ConnectFourNeuralNetworkInputData inputData,
			ConnectFourNeuralNetworkOutputData outputData) {
		trainingData.add(new SimpleEntry<ConnectFourNeuralNetworkInputData, ConnectFourNeuralNetworkOutputData>(
				inputData, outputData));
	}

	public ConnectFourNeuralNetworkOutputData compute(ConnectFourNeuralNetworkInputData inputData) {
		MLData mlInput = new BasicMLData(inputData.getData());
		MLData networkOutput = network.compute(mlInput);
		return new ConnectFourNeuralNetworkOutputData(networkOutput.getData());
	}

	private void createNeuralNetwork() {
		network = new BasicNetwork();
		network.addLayer(new BasicLayer(null, true, GameProperties.ROWS * GameProperties.COLS));
		network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 25));
		network.addLayer(new BasicLayer(new ActivationSigmoid(), false, 3));
		network.getStructure().finalizeStructure();
		network.reset();

		System.out.println("Network weights:");
		// System.out.println(network.dumpWeightsVerbose());
		PersistBasicNetwork persistor = new PersistBasicNetwork();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		persistor.save(outputStream, network);
		String networkAsString = new String(outputStream.toByteArray());
		System.out.println(networkAsString);
	}

	public BasicNetwork getBasicNetwork() {
		return network;
	}

	// TODO: Store the training set as an MLDataSet so we don't have to convert it
	// every time we train
	private MLDataSet getTrainingSet() {
		List<MLDataPair> dataPairs = new ArrayList<>();
		for (Entry<ConnectFourNeuralNetworkInputData, ConnectFourNeuralNetworkOutputData> entry : trainingData) {
			dataPairs.add(new BasicMLDataPair(new BasicMLData(entry.getKey().getData()),
					new BasicMLData(entry.getValue().getData())));
		}
		return new BasicMLDataSet(dataPairs);
	}

	public void train() {
		MLDataSet trainingSet = getTrainingSet();
		// Propagation train = new ResilientPropagation(network, trainingSet);
		Propagation train = new Backpropagation(network, trainingSet);
		int epoch = 1;
		do {
			train.iteration();
			System.out.println("Epoch #" + epoch + " Error: " + train.getError());
			epoch++;
		} while (train.getError() > targetErrorRate && epoch < maxEpochs);
		System.out.println("Epochs: " + epoch + " Error: " + train.getError());
		train.finishTraining();
	}
}
