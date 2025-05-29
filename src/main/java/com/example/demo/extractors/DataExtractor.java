package com.example.demo.extractors;

import com.example.demo.model.SanctionedEntityPWC;

import java.util.List;

public interface DataExtractor {
    List<SanctionedEntityPWC> extractData(String source) throws Exception;  // Add `throws Exception`
}
