package com.trms.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.trms.persistence.model.Address;

import java.io.IOException;
import java.util.ArrayList;
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
        List<Address> addresses = new ArrayList<Address>();
        for (Address address : addressList) {
            addresses.add(address);
        }
        gen.writeObject(addresses);
    }
}
