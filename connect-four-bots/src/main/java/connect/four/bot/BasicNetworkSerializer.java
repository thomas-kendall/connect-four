package connect.four.bot;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.PersistBasicNetwork;
import org.encog.persist.EncogPersistor;

public class BasicNetworkSerializer {

	public static BasicNetwork deserialize(String basicNetworkAsString) {
		EncogPersistor persistor = new PersistBasicNetwork();
		InputStream inputStream = new ByteArrayInputStream(basicNetworkAsString.getBytes(StandardCharsets.UTF_8));
		return (BasicNetwork) persistor.read(inputStream);
	}

	public static String serialize(BasicNetwork network) {
		EncogPersistor persistor = new PersistBasicNetwork();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		persistor.save(outputStream, network);
		return new String(outputStream.toByteArray());
	}

}
