package com.company;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class OutputGenerator {
    public void writeToFile(List<DataModel> dataModelList) {
        if (dataModelList.isEmpty()) {
            System.out.println("Nothing to write.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        mapper.setDateFormat(df);

        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
        JsonNode all = mapper.valueToTree(dataModelList);
        JsonNode firstObject = mapper.valueToTree(dataModelList.get(0));
        firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );
        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

        CsvMapper csvMapper = new CsvMapper();
        try {
            csvMapper.writerFor(JsonNode.class)
                    .with(csvSchema)
                    .writeValue(new File("pulls.csv"), all);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
