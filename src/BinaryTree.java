
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class BinaryTree {

    TreeNode root;

    public BinaryTree() {
        this.root = null;
    }

    public BinaryTree(TreeNode root) {
        this.root = root;
    }


    // 1. construccion del arbol


    public void insertRoot(Object data) {
        if (this.root == null) {
            this.root = new TreeNode(data);
        }
    }

    public TreeNode search(Object data) {
        return searchRec(this.root, data);
    }

    private TreeNode searchRec(TreeNode node, Object data) {
        if (node == null) return null;
        if (node.data.equals(data)) return node;

        TreeNode leftResult = searchRec(node.left, data);
        if (leftResult != null) return leftResult;

        return searchRec(node.right, data);
    }

    public boolean insertLeft(Object parent, Object data) {
        TreeNode parentNode = search(parent);
        if (parentNode == null || parentNode.left != null) {
            return false;
        }
        parentNode.left = new TreeNode(data);
        return true;
    }

    public boolean insertRight(Object parent, Object data) {
        TreeNode parentNode = search(parent);
        if (parentNode == null || parentNode.right != null) {
            return false;
        }
        parentNode.right = new TreeNode(data);
        return true;
    }

    public void buildTree(Scanner scanner) {
        System.out.print("Ingrese la raiz del arbol (vacio (enter) o '-' para cancelar): ");
        String rootData = scanner.nextLine().trim();

        if (rootData.isEmpty() || rootData.equals("-")) {
            this.root = null;
            return;
        }

        this.root = new TreeNode(rootData);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(this.root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();

            System.out.print("Hijo izquierdo de " + current.data + " (vacio (enter) o '-' para omitir): ");
            String leftData = scanner.nextLine().trim();
            if (!leftData.isEmpty() && !leftData.equals("-")) {
                current.left = new TreeNode(leftData);
                queue.add(current.left);
            }

            System.out.print("Hijo derecho de " + current.data + " (vacio (enter) o '-' para omitir): ");
            String rightData = scanner.nextLine().trim();
            if (!rightData.isEmpty() && !rightData.equals("-")) {
                current.right = new TreeNode(rightData);
                queue.add(current.right);
            }
        }
    }


    // 2. visualizacion


    public void printTree() {
        if (this.root == null) {
            System.out.println("(Arbol vacio)");
            return;
        }
        printNode(this.root, "", true, false);
    }

    private void printNode(TreeNode node, String prefix, boolean isTail, boolean showBalance) {
        if (node == null) return;

        String nodeInfo = node.data.toString();
        if (showBalance) {
            nodeInfo += " (BF: " + balanceFactor(node) + ")";
        }

        System.out.println(prefix + (isTail ? "└── " : "├── ") + nodeInfo);

        if (node.left != null || node.right != null) {
            printNode(node.left, prefix + (isTail ? "    " : "│   "), node.right == null, showBalance);
            if (node.right != null) {
                printNode(node.right, prefix + (isTail ? "    " : "│   "), true, showBalance);
            }
        }
    }


    // 3. recorridos


    public String preorder() {
        return preorderRec(this.root).trim();
    }

    private String preorderRec(TreeNode node) {
        if (node == null) return "";
        return node.data + " " + preorderRec(node.left) + preorderRec(node.right);
    }

    public String inorder() {
        return inorderRec(this.root).trim();
    }

    private String inorderRec(TreeNode node) {
        if (node == null) return "";
        return inorderRec(node.left) + node.data + " " + inorderRec(node.right);
    }

    public String postorder() {
        return postorderRec(this.root).trim();
    }

    private String postorderRec(TreeNode node) {
        if (node == null) return "";
        return postorderRec(node.left) + postorderRec(node.right) + node.data + " ";
    }


    // 4. factores de equilibrio


    public int height(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public int balanceFactor(TreeNode node) {
        if (node == null) return 0;
        return height(node.right) - height(node.left);
    }

    public boolean isBalanced() {
        return isBalancedRec(this.root);
    }

    private boolean isBalancedRec(TreeNode node) {
        if (node == null) return true;
        int bf = balanceFactor(node);
        if (bf < -1 || bf > 1) return false;
        return isBalancedRec(node.left) && isBalancedRec(node.right);
    }

    public List<Object> unbalancedNodes() {
        List<Object> list = new ArrayList<>();
        findUnbalanced(this.root, list);
        return list;
    }

    private void findUnbalanced(TreeNode node, List<Object> list) {
        if (node == null) return;
        int bf = balanceFactor(node);
        if (bf < -1 || bf > 1) {
            list.add(node.data);
        }
        findUnbalanced(node.left, list);
        findUnbalanced(node.right, list);
    }

    public void printBalanceFactors() {
        if (this.root == null) {
            System.out.println("(Arbol vacio)");
            return;
        }
        printNode(this.root, "", true, true);
    }


    // 5. Balanceo AVL


    public TreeNode rotateRight(TreeNode node) {
        TreeNode x = node.left;
        TreeNode T = x.right;
        x.right = node;
        node.left = T;
        return x;
    }

    public TreeNode rotateLeft(TreeNode node) {
        TreeNode y = node.right;
        TreeNode T = y.left;
        y.left = node;
        node.right = T;
        return y;
    }

    public TreeNode rotateLeftRight(TreeNode node) {
        node.left = rotateLeft(node.left);
        return rotateRight(node);
    }

    public TreeNode rotateRightLeft(TreeNode node) {
        node.right = rotateRight(node.right);
        return rotateLeft(node);
    }

    public void balance() {
        System.out.println("\n--- Arbol ANTES de balancear ---");
        printBalanceFactors();


        boolean needsBalancing = true;
        while (needsBalancing) {
            int unbalCount = unbalancedNodes().size();
            this.root = balanceSubtree(this.root);
            if (unbalancedNodes().size() == 0 || unbalancedNodes().size() == unbalCount) {
                needsBalancing = false;
            }
        }

        System.out.println("\n--- Arbol DESPUES de balancear ---");
        printBalanceFactors();
    }

    private TreeNode balanceSubtree(TreeNode node) {
        if (node == null) return null;

        // recorrido posorden para balancear de abajo hacia arriba
        node.left = balanceSubtree(node.left);
        node.right = balanceSubtree(node.right);

        int bf = balanceFactor(node);

        // desbalance a la izquierda
        if (bf < -1) {
            int leftBf = balanceFactor(node.left);
            if (leftBf <= 0) {
                return rotateRight(node);     // caso LL
            } else {
                return rotateLeftRight(node); // caso LR
            }
        }

        // Desbalance cargado a la derecha
        if (bf > 1) {
            int rightBf = balanceFactor(node.right);
            if (rightBf >= 0) {
                return rotateLeft(node);      // caso RR
            } else {
                return rotateRightLeft(node); // caso RL
            }
        }

        return node;
    }




    /*
    IMPORTANTE: respete EXACTAMENTE los nombres y firmas de los metodos que se
    piden abajo; la revision automatica los busca por nombre. Puede agregar
    todos los metodos auxiliares (privados) que necesite.
    */

    /*
    1. Construccion del arbol: implemente aqui

    public void insertRoot(Object data)
        Si el arbol esta vacio, crea la raiz con 'data'.

    public TreeNode search(Object data)
        Devuelve el nodo cuyo dato es igual (equals) a 'data', o null si no
        existe.

    public boolean insertLeft(Object parent, Object data)
        Agrega 'data' como hijo izquierdo del nodo cuyo dato es 'parent'.
        Devuelve false (y no modifica nada) si 'parent' no existe o si ya
        tiene hijo izquierdo; true en otro caso.

    public boolean insertRight(Object parent, Object data)
        Igual que insertLeft, pero con el hijo derecho.

    public void buildTree(Scanner scanner)
        Construye el arbol leyendo los datos con 'scanner' (en el main se le
        pasa new Scanner(System.in)). Primero se lee la raiz y luego, por
        niveles, el hijo izquierdo y el hijo derecho de cada nodo
        ("Hijo izquierdo de A: ", "Hijo derecho de A: ", ...). Una linea
        vacia o "-" significa que ese hijo no existe.
    */

    /*
    2. Visualizacion: implemente aqui

    public void printTree()
        Muestra el arbol en consola de forma grafica, indicando claramente
        la relacion entre padres e hijos.
    */

    /*
    3. Recorridos: implemente aqui. Cada recorrido debe ser una funcion
    RECURSIVA independiente (por ejemplo, un metodo privado que reciba un
    TreeNode). Los metodos publicos devuelven la secuencia separada por
    espacios, p.ej. "A B D H I E C F G".

    public String preorder()
    public String inorder()
    public String postorder() preimraskajdlksjd
    */

    /*
    4. Factores de equilibrio: implemente aqui

    public int height(TreeNode node)
        Altura del subarbol que empieza en 'node' (null tiene altura 0 y una
        hoja altura 1).

    public int balanceFactor(TreeNode node)
        Factor de equilibrio de 'node': height(node.right) - height(node.left).

    public boolean isBalanced()
        true si TODOS los nodos tienen factor de equilibrio -1, 0 o 1.

    public List<Object> unbalancedNodes()
        Lista con los datos de los nodos cuyo factor de equilibrio es menor
        que -1 o mayor que 1 (los que requieren balanceo).

    public void printBalanceFactors()
        Muestra el arbol en consola indicando en cada nodo su factor de
        equilibrio.
    */

    /*
    5. Balanceo AVL: implemente aqui. Cada rotacion recibe la raiz del
    subarbol desbalanceado y devuelve la NUEVA raiz de ese subarbol.

    public TreeNode rotateRight(TreeNode node)       rotacion simple a la derecha
    public TreeNode rotateLeft(TreeNode node)        rotacion simple a la izquierda
    public TreeNode rotateLeftRight(TreeNode node)   rotacion doble izquierda-derecha
    public TreeNode rotateRightLeft(TreeNode node)   rotacion doble derecha-izquierda

    public void balance()
        Identifica los nodos desbalanceados y aplica las rotaciones necesarias
        hasta que el arbol sea AVL (actualiza 'root' si cambia). Debe mostrar
        el arbol con sus factores de equilibrio antes y despues de balancear.
    */
}
