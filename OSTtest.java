// This java file is about implementing Order-Statistic Tree
// Reference book <Introduction to Algorithm> Third Edition
// Author: Yinjie Huang
// Data: 07-26-2014

class OSTtest {
        public static void main(String[] args) {
                System.out.println("Trying to implement Order-Statistic Tree: ");
                OSTree ost = new OSTree();
                int[] keys = new int[]{41,38,31,12,19,8};
                for (int i=0;i<keys.length;i++) {
                        ost.RBinsert(keys[i]);
                }
                ost.inorderTreeWalk(ost.getRoot());
                
                int Rank = 3;
                System.out.println("Select "+ Rank +"th Rank: ");
                ost.printKey(ost.OSSelect(ost.getRoot(),Rank));

                System.out.println("Print Rank: " + ost.OSRank(ost.search(ost.getRoot(),41)));
                //System.out.println("Let's delete:");
                //ost.delete(8);
                //ost.delete(12);
                //ost.delete(19);
                //ost.delete(31);
                //ost.inorderTreeWalk(ost.getRoot());
        }
}

class OSTree {
        private class Node {
                private int key;
                private int size;
                private String color;
                private Node p;
                private Node left;
                private Node right;

                public Node(int key, int size, String color, Node p, Node left, Node right) {
                        this.key = key;
                        this.size = size;
                        this.color = color;
                        this.p = p;
                        this.left = left;
                        this.right = right;
                }
        }

        private Node Tnill = new Node(-100,0, "BLACK", null, null, null);
        private Node root = Tnill;

        public void printKey(Node x) {
                if (x == Tnill) {
                        System.out.println("NULL node, can not print!!");
                } else {
                        System.out.println("Key: "+x.key+". Size: "+x.size+". Color: "+x.color+". Parent: "+x.p.key+". Left: "+x.left.key+". Right: "+x.right.key+".");
                }
        }

        public Node getRoot() {
                return root;
        }

        public void inorderTreeWalk(Node x) {
                if (x != Tnill) {
                        inorderTreeWalk(x.left);
                        printKey(x);
                        inorderTreeWalk(x.right);
                }
        }

        public Node search(Node x, int key) {
                if (x.key == key || x == Tnill) {
                        return x;
                } else if (x.key < key) {
                        return search(x.right, key);
                } else {
                        return search(x.left, key);
                }
        }

        public Node min(Node x) {
                while (x.left != Tnill) {
                        x = x.left;
                }
                return x;
        }

        public Node max(Node x) {
                while (x.right != Tnill) {
                        x = x.right;
                }
                return x;
        }

        public Node OSSelect(Node x, int i) {
                int r = x.left.size + 1;
                if (r == i) {
                        return x;
                } else if (i < r) {
                        return OSSelect(x.left, i);
                } else {
                        return OSSelect(x.right, i-r);
                }
        }

        public int OSRank(Node x) {
                int r = x.left.size + 1;
                Node y = x;
                while (y != root) {
                        if (y.p.right == y) {
                                r = r + y.p.left.size + 1;
                        }
                        y = y.p;
                }
                return r;
        }

        private void leftRotate(Node x) {
                Node y = x.right;
                x.right = y.left;
                if (y.left != Tnill) {
                        y.left.p = x;
                }
                y.p = x.p;
                if (x.p == Tnill) {
                        root = y;
                } else if (x.p.left == x) {
                        x.p.left = y;
                } else {
                        x.p.right = y;
                }
                y.left = x;
                x.p = y;
                y.size = x.size;
                x.size = x.left.size + x.right.size +1;
        }

        private void rightRotate(Node y) {
                Node x = y.left;
                y.left = x.right;
                if (x.right != Tnill) {
                        x.right.p = y;
                }
                x.p = y.p;
                if (y.p == Tnill) {
                        root = x;
                } else if (y.p.left == y) {
                        y.p.left = x;
                } else {
                        y.p.right = x;
                }
                x.right = y;
                y.p = x;
                x.size = y.size;
                y.size = y.left.size + y.right.size + 1;
        }

        public void RBinsert(int key) {
                Node y = Tnill;
                Node x = root;
                Node z = new Node(key,1,"RED",Tnill,Tnill,Tnill);
                while (x != Tnill) {
                        y = x;
                        y.size = y.size + 1;
                        if (z.key < x.key) {
                                x = x.left;
                        } else {
                                x = x.right;
                        }
                }
                z.p = y;
                if (y == Tnill) {
                        root = z;
                } else if (z.key < y.key) {
                        y.left = z;
                } else {
                        y.right = z;
                }
                RBinsertFix(z);
        }
        
        private void RBinsertFix(Node z) {
                while (z.p.color.equals("RED")) {
                        if (z.p == z.p.p.left) {
                                Node y = z.p.p.right; // Uncle
                                if (y.color.equals("RED")) {
                                        z.p.color = "BLACK";
                                        y.color = "BLACK";
                                        z.p.p.color = "RED";
                                        z = z.p.p;
                                } else {
                                        if (z == z.p.right) {
                                                z = z.p;
                                                leftRotate(z);
                                        }
                                        z.p.color = "BLACK";
                                        z.p.p.color = "RED";
                                        rightRotate(z.p.p);
                                }
                        } else {
                                Node y = z.p.p.left; // Uncle
                                if (y.color.equals("RED")) {
                                        z.p.color = "BLACK";
                                        y.color = "BLACK";
                                        z.p.p.color = "RED";
                                        z = z.p.p;
                                } else {
                                        if (z == z.p.left) {
                                                z = z.p;
                                                rightRotate(z);
                                        }
                                        z.p.color = "BLACK";
                                        z.p.p.color = "RED";
                                        leftRotate(z.p.p);
                                }
                        }
                }
                root.color = "BLACK";
        }

        private void RBtransplant(Node u, Node v) {
                if (u.p == Tnill) {
                        root = v;
                } else if (u.p.left == u) {
                        u.p.left = v;
                } else {
                        u.p.right = v;
                }
                v.p = u.p;
        }

        public void delete(int key) {
                Node x = search(root, key);
                if (x == Tnill) {
                        System.out.println("Sorry, can not delete since the tree does not contain this node, dumbass");
                } else { 
                        Node y = x;
                        while (y != Tnill) {
                                y.size = y.size - 1;
                                y = y.p;
                        }
                        delete(x);
                }
        }
                
        // Delete a node in the tree
        private void delete(Node z) {
                Node y = z;
                Node x;
                String y_o_color = y.color;
                if (z.left == Tnill) {
                        x = z.right;
                        RBtransplant(z, z.right);
                } else if (z.right == Tnill) {
                        x = z.left;
                        RBtransplant(z, z.left);
                } else {
                        y = min(z.right);
                        y_o_color = y.color;
                        x = y.right;
                        if (y.p == z) {
                                x.p = y;
                        } else {
                                RBtransplant(y, y.right);
                                y.right = z.right;
                                y.right.p = y;
                        }
                        RBtransplant(z, y);
                        y.left = z.left;
                        y.left.p = y;
                        y.color = z.color;
                }
                if (y_o_color.equals("BLACK")) {
                        RBdeleteFix(x);
                }
        }

        // Fix the Red-Black property, please read Ch 13 from <Introduction to Algorithm>
        private void RBdeleteFix(Node x) {
                while (x != root && x.color.equals("BLACK")) {
                        if (x == x.p.left) {
                                Node w = x.p.right;
                                if (w.color.equals("RED")) {
                                        w.color = "BLACK";
                                        x.p.color = "RED";
                                        leftRotate(x.p);
                                        w = x.p.right;
                                }
                                if (w.left.color.equals("BLACK") && w.right.color.equals("BLACK")) {
                                        w.color = "RED";
                                        x = x.p;
                                } else {
                                        if (w.right.color.equals("BLACK")) {
                                                w.left.color = "BLACK";
                                                w.color = "RED";
                                                rightRotate(w);
                                                w = x.p.right;
                                        }
                                        w.color = x.p.color;
                                        x.p.color = "BLACK";
                                        w.right.color = "BLACK";
                                        leftRotate(x.p);
                                        x = root;
                                }
                        } else {
                                Node w = x.p.left;
                                if (w.color.equals("RED")) {
                                        w.color = "BLACK";
                                        x.p.color = "RED";
                                        rightRotate(x.p);
                                        w = x.p.left;
                                }
                                if (w.left.color.equals("BLACK") && w.right.color.equals("BLACK")) {
                                        w.color = "RED";
                                        x = x.p;
                                } else {
                                        if (w.left.color.equals("BLACK")) {
                                                w.right.color = "BLACK";
                                                w.color = "RED";
                                                leftRotate(w);
                                                w = x.p.left;
                                        }
                                        w.color = x.p.color;
                                        x.p.color = "BLACK";
                                        w.left.color = "BLACK";
                                        rightRotate(x.p);
                                        x = root;
                                }
                        }
                        x.color = "BLACK";
                }
        }
}
