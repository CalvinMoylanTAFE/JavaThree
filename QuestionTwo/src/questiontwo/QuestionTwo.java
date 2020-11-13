package questiontwo;

import java.util.Scanner;

class Node {

    String Value;
    Node left;
    Node right;

    public Node(String Value) {
        this.Value = Value;
        this.left = null;
        this.right = null;
    }
}

class BinaryTree {

    Node root;

    public BinaryTree(String rootValue) {
        add(rootValue);
    }

    private Node addRecursive(Node node, String valueToFind) {
        if (node == null) {
            return new Node(valueToFind);
        }

        if (valueToFind.compareTo(node.Value) < 0) {
            node.left = addRecursive(node.left, valueToFind);
        } else if (valueToFind.compareTo(node.Value) > 0) {
            node.right = addRecursive(node.right, valueToFind);
        } else {
            return node;
        }

        return node;
    }

    private Node removeRecursive(Node node, String valueToFind) {
        if (node == null) {
            return null;
        }

        if (valueToFind.compareTo(node.Value) == 0) {
            if (node.left == null && node.right == null) {
                return null;
            }

            if (node.left == null) {
                return null;
            } else if (node.right == null) {
                return null;
            } else {
                String value = findSmallest(node.right);
                node.Value = value;
                node.right = removeRecursive(node.right, value);
                return node;
            }
        } else if (valueToFind.compareTo(node.Value) < 0) {
            node.left = removeRecursive(node.left, valueToFind);
            return node;
        }

        node.right = removeRecursive(node.right, valueToFind);
        return node;
    }

    private String findSmallest(Node node) {
        if (node.left == null) {
            return node.Value;
        } else {
            return findSmallest(node.left);
        }
    }

    private boolean searchTree(Node node, String item) {
        if (node == null) {
            return false;
        } else if (node.Value.equals(item)) {
            return true;
        }
        boolean result;
        result = searchTree(node.left, item);
        if (result) {
            return result;
        }
        result = searchTree(node.right, item);
        return result;
    }

    public boolean SearchTree(String item) {
        return searchTree(root, item);
    }

    private void walkTree(Node node) {
        if (node == null) {
            return;
        }

        walkTree(node.left);
        System.out.print(node.Value + " | ");
        walkTree(node.right);
    }

    public void walk() {
        walkTree(root);
        System.out.println("");
    }

    public void remove(String valueToFind) {
        root = removeRecursive(root, valueToFind);
    }

    public void add(String valueToFind) {
        root = addRecursive(root, valueToFind);
    }

}

public class QuestionTwo {

    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree("Root");

        bt.add("Screws");
        bt.add("Bolts");
        bt.add("Studs");
        bt.add("Nuts");
        bt.add("Washers");
        bt.add("Rivets");
        bt.add("Inserts");
        bt.add("Standoffs");
        bt.add("Thread inserts");
        bt.add("Blind rivet nuts");

        bt.walk();
        

        while (true) {
            System.out.println("Would you like to add, remove, walk or search tree?");
            String input = getInput();
            switch (input) {
                case "walk":
                    bt.walk();
                    break;
                case "add":
                    System.out.println("What would you like to add to the tree?");
                    input = getInput();
                    bt.add(input);
                    break;
                case "remove":
                    System.out.println("What would you like to remove from the tree?");
                    input = getInput();
                    bt.remove(input);
                    break;
                case "search":
                    System.out.println("What would you like to search?");
                    input = getInput();
                    boolean result = bt.SearchTree(input);
                    if (result) {
                        System.out.println("Found " + input);
                        continue;
                    }
                    System.out.println("Could not find " + input);
                    break;
                default:
                    break;
            }
        }
    }

    public static String getInput() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
