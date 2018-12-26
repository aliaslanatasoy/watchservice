import java.nio.file.*;
import java.util.Scanner;

public class WatchServiceExample {
    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path you want to listen: ");
        String filePath = scanner.nextLine();
        Path path = Paths.get( filePath);
        WatchService watchService = path.getFileSystem().newWatchService();
        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                switch (event.kind().name()){
                    case "ENTRY_CREATE":
                        System.out.println(event.context() + " is created");
                        break;
                    case "ENTRY_DELETE":
                        System.out.println(event.context() + " is deleted");
                        break;
                    case "ENTRY_MODIFY":
                        System.out.println(event.context() + " is modified");
                        break;
                }
            }
            key.reset();
        }
    }

}
