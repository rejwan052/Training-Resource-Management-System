package com.trms.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.trms.persistence.model.Address;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class CustomAddressSerializer extends StdSerializer<List<Address>> {

    public CustomAddressSerializer() {
        this(null);
    }

    public CustomAddressSerializer(Class<List<Address>> t) {
        super(t);
    }

    @Override
    public void serialize(List<Address> addressList, JsonGenerator gen, SerializerProvider provider) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        ArrayNode arrayNode = mapper.createArrayNode();

        if(!CollectionUtils.isEmpty(addressList)){
            for(Address address : addressList){
                ObjectNode objectNode = mapper.createObjectNode();

                objectNode.put("id", address.getId());
                objectNode.put("addressLine1", address.getAddressLine1());
                objectNode.put("addressLine2", address.getAddressLine2());
                objectNode.put("city", address.getCity());
                objectNode.put("town", address.getTown());

                arrayNode.add(objectNode);
            }
        }
        gen.writeObject(arrayNode);
    }
}
