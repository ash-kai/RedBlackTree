import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Ash on 3/18/16.
 */
public class bbst {
    public static void main(String[] args) {
        File inputFile = null;
        Scanner scanner = null;
        try {
            inputFile = new File(args[0]);
            scanner = new Scanner(inputFile);
            String line;

            //Create a null Node object
            Node none = new Node(0, 0, null);

            RedBlackBST root = new RedBlackBST(none);

            line = scanner.nextLine();

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] nodeValues = line.split(" ");
                Node node = new Node(Integer.parseInt(nodeValues[0]), Integer.parseInt(nodeValues[1]), none);
                root.insertTree(node, none);
            }
            System.out.println("###############  Done  ################");

            scanner.close();


            //Read from Commands File
            String filename = "/home/Ash/IdeaProjects/Red Black Tree/src/commands.txt";

            inputFile = new File(filename);
            scanner = new Scanner(inputFile);
            int id, count;
            Node node;
            //Node rootNode = root.getRootNode();
            boolean result;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] cmdContents = line.split(" ");
                id = 0;
                if (!cmdContents[0].toLowerCase().equals("quit")) {
                    id = Integer.parseInt(cmdContents[1]);
                }
                switch (cmdContents[0].toLowerCase()) {
                    case "increase":
                        count = Integer.parseInt(cmdContents[2]);
                        result = root.increase(id, count);
                        if (!result) {
                            node = new Node(id, count, none);
                            root.insertTree(node, none);
                            System.out.println(count);
                        }
                        break;
                    case "reduce":
                        count = Integer.parseInt(cmdContents[2]);
                        result = root.reduce(id, count);
                        if (result) {
                            node = root.getNode(id);
                            root.rbDelete(node, none);
                        }
                        break;
                    case "inrange":
                        int id2 = Integer.parseInt(cmdContents[2]);
                        int cnt = root.inRange(id, id2);
                        System.out.println(cnt);
                        break;
                    case "count":
                        root.Count(id);
                        break;
                    case "next":
                        node = root.nextNode(id);
                        System.out.println(node.ID + " " + node.count);
                        break;
                    case "previous":
                        node = root.previousNode(id);
                        System.out.println(node.ID + " " + node.count);
                        break;
                    case "quit":
                        break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Error" + ex.toString());
        } catch (FileNotFoundException ex) {
            System.out.println("Error File : " + ex.toString());
        } catch (Exception ex) {
            System.out.println("Error General : " + ex.toString());
        }
    }
}
