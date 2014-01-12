package bg.unisofia.fmi.docmag.utils;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class JsonObjectIdSerializer extends JsonSerializer<ObjectId> {

	@Override
	public void serialize(ObjectId objectId, JsonGenerator jsonGenerator,
			SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		jsonGenerator.writeString(objectId.toString());
		
	}

}
