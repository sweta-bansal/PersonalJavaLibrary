public class ParseXML{


    private List<String> fieldNames;
    private List<String> fieldTypes;
    private List<String> lengths;
    private List<String> required;

    public ParseXML(String uri) {

        Document doc = new ParseXML(uri).getDoc();
        parseFields(doc);
    }

    public static void main(String[] args) {

        ParseXML p = new ParseXML("");
//        System.out.println(p.uri);
//        System.out.println(p.fieldNames.size());
//        System.out.println(p.fieldNames);
//        System.out.println(p.fieldTypes);
//        System.out.println(p.getLengths());
//        System.out.println(p.getRequired());

    }

    public void parseFields(Document doc) {

        fieldNames = new ArrayList<>();
        fieldTypes = new ArrayList<>();
        lengths = new ArrayList<>();
        required = new ArrayList<>();
        NodeList nodes = doc.getElementsByTagName("column");
        NodeList nodes2 = doc.getElementsByTagName("partitionKey");
        partitionKey = nodes2.item(0).getTextContent();

        for (int i = 0; i < nodes.getLength() - 1; i++) {
            Node node = nodes.item(i);
            Element e = (Element) node;
            fieldNames.add(e.getElementsByTagName("name").item(0).getTextContent());
            fieldTypes.add(e.getElementsByTagName("type").item(0).getTextContent());
            try {
                lengths.add(e.getElementsByTagName("size").item(0).getTextContent());
            } catch (NullPointerException ignored) {
                lengths.add("");
            }
            try {
                required.add(e.getElementsByTagName("required").item(0).getTextContent());
            } catch (NullPointerException ignored) {
                required.add("");
            }
        }
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }

    public List<String> getTypes() {
        return fieldTypes;
    }

    public List<String> getLengths() {
        return lengths;
    }

    public List<String> getRequired() {
        return required;
    }
}
