package za.co.oosthuizen.remote.model;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author Andre Oosthuizen
 *
 */
@Component
public class BooleanSerializer extends JsonSerializer<Boolean> {

	@Override
	public void serialize(Boolean bool, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		if (bool != null && bool) {
			jsonGenerator.writeString("ON");
		} else {
			jsonGenerator.writeString("OFF");
		}
	}

}
