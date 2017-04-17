def number = 0
def filePath = new File('C:\\BuildArea\\eventsourcing-kafka\\event-sourcing\\event-souring-kafka\\event-infrastructure\\src\\main\\resources\\kafka-producer.properties').eachLine {
    line -> number++
        println "$number : $line"
};


def classes = [String, List, File]
for (clazz in classes) {
    println clazz.package.name
}