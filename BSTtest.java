// This Java file is about implenmenting Binary Search Tree
// Reference book is <Introduction to ALogirhtm> third edition
// Author: Yinjie Huang
// Data: 07-22-2014


class Test {
        static public void main(String[] args) {
                MyBST mbst = new MyBST();
                int[] keys = new int[]{15,6,18,3,7,13,20,2,9,4};
                // Insert 
                for (int i=0;i<keys.length;i++) {
                        mbst.insert(keys[i]);
                }
                // First print out the root key value
                System.out.print("(1) let's print out the root key: ");
                mbst.printKey(mbst.getRoot());
                
                // Second print out the searched key value
                int key = 210;
                System.out.print("(2) let's see if we can find "+key+": ");
                mbst.printKey(mbst.search(mbst.getRoot(),key));
                
                // Third print out the minimum key value
                System.out.print("(3) let's see the minimum key: ");
                mbst.printKey(mbst.min(mbst.getRoot()));

                // Fourth print out the maximum key value
                System.out.print("(4) let's see the maximum key: ");
                mbst.printKey(mbst.max(mbst.getRoot()));

                // Fifth print out the successor key value
                System.out.print("(5) let's see " + 13 + " successor key: ");
                mbst.printKey(mbst.successor(mbst.search(mbst.getRoot(),13)));

                // Sixth print out the succssor key value
                System.out.print("(6) let's see " + 7 + " predecessor key: ");
                mbst.printKey(mbst.predecessor(mbst.search(mbst.getRoot(),7)));

                // Seventh, let's delete something
                System.out.print("(7) let's delete " + 7 + " :");
                mbst.delete(7);
                mbst.inorderTreeWalk(mbst.getRoot());
        }
}


class MyBST {
        // Construct the node
        private Node root = null;
        private class Node {
                private int key;
                private Node left;
                private Node right;
                private Node p;
        
                public Node(int key, Node left, Node right, Node p) {
                        this.key = key;
                        this.left = left;
                        this.right = right;
                        this.p = p;
                }
        }

        // Print the key value in the node
        public void printKey(Node x) {
                if (x!=null) {
                        System.out.println("Key value: "+x.key+".");
                } else {
                        System.out.println("Null, Sorry");
                }
        }

        public Node getRoot() {
                return root;
        }
        
        public Node search(Node x, int key) {
                if (x==null || key == x.key) {
                        return x;
                } else if (key < x.key) {
                        return search(x.left,key);
                } else {
                        return search(x.right,key);
                }
        }

        // Return the minimum key node
        public Node min(Node x) {
                while (x.left != null) {
                        x = x.left;
                }
                return x;
        }

        public Node max(Node x) {
                while (x.right != null) {
                        x = x.right;
                }
                return x;
        }

        public Node successor(Node x) {
                if (x == null) {
                        return x;
                }
                if (x.right!=null) {
                        return min(x.right);
                }
                Node y = x.p;
                while (y!=null && x == y.right) {
                        x = y;
                        y = y.p;
                }
                return y;
        }

        public Node predecessor(Node x) {
                if (x == null)
                        return x;
                if (x.left != null) {
                        return max(x.left);
                }
                Node y = x.p;
                while (y != null && x == y.left) {
                        x = y;
                        y = y.p;
                }
                return y;
        }


        // Insert a new node with specified key value
        public void insert(int key) {
                Node y = null;
                Node x = root;
                Node z = new Node(key, null, null, null);
                while (x!=null) {
                        y = x;
                        if (z.key < x.key) {
                                x = x.left;
                        }else {
                                x = x.right;
                        }
                }
                z.p = y;
                if (y == null) {
                        root = z;
                } else if (z.key < y.key) {
                        y.left = z;
                } else {
                        y.right = z;
                }
        }

        private void transplant(Node u, Node v) {
                if (u.p == null) {
                        root = v;
                } else if (u == u.p.left) {
                        u.p.left = v;
                } else {
                        u.p.right = v;
                }
                if (v != null) {
                        v.p = u.p;
                }
        }

        // Delete some specified node
        private void delete(Node z) {
                if (z.left == null) {
                        transplant(z,z.right);
                } else if (z.right == null) {
                        transplant(z,z.left);
                } else {
                        Node y = min(z.right);
                        if (y.p != z) {
                                transplant(y,y.right);
                                y.right = z.right;
                                y.right.p = y;
                        }
                        transplant(z,y);
                        y.left = z.left;
                        y.left.p = y;
                }
        }

        public void delete(int key) {
                Node z = search(root,key);
                if (z == null) {
                        System.out.println("Soryy, can not find it");
                } else {
                        delete(z);
                }
        }

        public void inorderTreeWalk(Node x) {
                if (x!=null) {
                        inorderTreeWalk(x.left);
                        printKey(x);
                        inorderTreeWalk(x.right);
                }
        }
}
