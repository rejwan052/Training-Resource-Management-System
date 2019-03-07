package com.trms.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.trms.persistence.model.Employee;

import java.io.IOException;

public class CustomEmployeeSerializer extends StdSerializer<Employee> {

    public CustomEmployeeSerializer() {
        super(Employee.class);
    }

    public CustomEmployeeSerializer(Class<Employee> t) {
        super(t);
    }

    @Override
    public void serialize(Employee employee, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeFieldName("id");
        generator.writeString(employee.getId().toString());
        generator.writeFieldName("fullName");
        generator.writeString(employee.getFullName());
        generator.writeFieldName("designation");
        generator.writeNumber(employee.getDesignation().getName());
        generator.writeFieldName("department");
        generator.writeNumber(employee.getDepartment().getName());
        generator.writeEndObject();
    }
}
