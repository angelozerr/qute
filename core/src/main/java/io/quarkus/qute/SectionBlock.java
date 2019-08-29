package io.quarkus.qute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Each section tag consists of one or more blocks. The main block is always present. Additional blocks start with a label
 * definition: <code>{:label param1}</code>.
 */
public class SectionBlock {

    static SectionBlock.Builder builder(String id) {
        return new Builder(id);
    }

    /**
     * Id generated by the parser. {@code main} for the main block.
     */
    public final String id;
    /**
     * Label used for the given part. {@code main} for the main block.
     */
    public final String label;
    /**
     * Map of parsed parameters.
     */
    public final Map<String, String> parameters;
    /**
     * Section content.
     */
    final List<TemplateNode> nodes;

    public SectionBlock(String id, String label, Map<String, String> parameters, List<TemplateNode> nodes) {
        this.id = id;
        this.label = label;
        this.parameters = parameters;
        this.nodes = ImmutableList.copyOf(nodes);
    }

    static class Builder {

        private final String id;
        private String label;
        private final Map<String, String> parameters;
        private final List<TemplateNode> nodes;

        public Builder(String id) {
            this.id = id;
            this.parameters = new HashMap<>();
            this.nodes = new ArrayList<>();
        }

        SectionBlock.Builder addNode(TemplateNode node) {
            nodes.add(node);
            return this;
        }

        SectionBlock.Builder addNodes(TemplateNode... nodes) {
            Collections.addAll(this.nodes, nodes);
            return this;
        }

        SectionBlock.Builder setLabel(String label) {
            this.label = label;
            return this;
        }

        SectionBlock.Builder addParameter(String name, String value) {
            this.parameters.put(name, value);
            return this;
        }

        String getLabel() {
            return label;
        }

        SectionBlock build() {
            return new SectionBlock(id, label, parameters, nodes);
        }
    }

}