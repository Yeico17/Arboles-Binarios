
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Ejemplo: arbol del enunciado
        //            A
        //          /   \
        //         B     C
        //        / \   / \
        //       D   E F   G
        //      / \
        //     H   I
        TreeNode a = new TreeNode("A");
        BinaryTree tree = new BinaryTree(a);

        a.left = new TreeNode("B");
        a.right = new TreeNode("C");
        a.left.left = new TreeNode("D");
        a.left.right = new TreeNode("E");
        a.right.left = new TreeNode("F");
        a.right.right = new TreeNode("G");
        a.left.left.left = new TreeNode("H");
        a.left.left.right = new TreeNode("I");

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 8) {
            System.out.println("\n--------------- MENU ---------------");
            System.out.println("1. Construir arbol");
            System.out.println("2. Mostrar arbol");
            System.out.println("3. Recorrido Preorden");
            System.out.println("4. Recorrido Inorden");
            System.out.println("5. Recorrido Posorden");
            System.out.println("6. Mostrar factores de equilibrio");
            System.out.println("7. Balancear arbol ");
            System.out.println("8. Salir");
            System.out.println("----------------------------");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un numero valido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    tree = new BinaryTree();
                    tree.buildTree(scanner);
                    break;
                case 2:
                    System.out.println("\nEstructura del arbol:");
                    tree.printTree();
                    break;
                case 3:
                    System.out.println("\nPreorden: " + tree.preorder());
                    break;
                case 4:
                    System.out.println("\nInorden: " + tree.inorder());
                    break;
                case 5:
                    System.out.println("\nPosorden: " + tree.postorder());
                    break;
                case 6:
                    System.out.println("\nArbol con factores de equilibrio:");
                    tree.printBalanceFactors();
                    break;
                case 7:
                    tree.balance();
                    break;
                case 8:
                    System.out.println("\nSaliendo del programa...");
                    break;
                default:
                    System.out.println("\nOpcion invalida, intente nuevamente.");
            }


        /*
        Implemente aqui el menu (cada opcion llama al metodo de BinaryTree
        indicado entre parentesis):
        ========== MENU ==========
        1. Construir arbol                  (buildTree(new Scanner(System.in)))
        2. Mostrar arbol                    (printTree())
        3. Recorrido Preorden               (preorder())
        4. Recorrido Inorden                (inorder())
        5. Recorrido Posorden               (postorder())
        6. Mostrar factores de equilibrio   (printBalanceFactors())
        7. Balancear arbol (AVL)            (balance())
        8. Salir
        ==========================
        Seleccione una opcion:
        */
        }
    }
}
