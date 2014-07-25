// This java file is about implementing Red-Black Tree
// Reference book <Introduction to Algorithm> third Edition
// Author: Yinjie Huang
// Date: 07-24-2014


class Test
{
        public static void main(String[] args)
        {
                System.out.println("Trying to implement Red-Black Tree (Cao): ");
                //String s = "Black";
                //System.out.println(s.equals("Black")?"Black":"Red");

                RBTree rbt = new RBTree();
                int[] keys = new int[]{41,38,31,12,19,8};

                for(int i=0;i<keys.length;i++)
                        rbt.RBinsert(keys[i]);
                //rbt.printKey(rbt.getRoot());
                rbt.inorderTreeWalk(rbt.getRoot());

                //rbt.printKey(rbt.search(rbt.getRoot(),7));

                //rbt.printKey(rbt.search(rbt.getRoot(),111));

                System.out.println("Let's delete:");
                rbt.delete(8);
                rbt.delete(12);
                rbt.delete(19);
                rbt.delete(31);

                rbt.inorderTreeWalk(rbt.getRoot());
        }
}

class RBTree
{
        // Construct Red-Black Tree
        private class Node{
                private int key;
                private String color;
                private Node left;
                private Node right;
                private Node p;

                public Node(int key, String color, Node left, Node right, Node p)
                {
                        this.key = key;
                        this.color = color;
                        this.left = left;
                        this.right = right;
                        this.p = p;
                }
        }

        // Create Sentinel Node
        private Node Tnill = new Node(-100,"BLACK",null,null,null);

        private Node root = Tnill;

        public void printKey(Node x)
        {
                if (x == Tnill)
                        System.out.println("It's empty!!");
                else
                        System.out.println("Key: "+x.key+"("+x.color+"). Left: "+x.left.key+". Right: "+x.right.key+".");
        }

        public Node getRoot()
        {
                return root;
        }

        public void inorderTreeWalk(Node x)
        {
                if (x != Tnill)
                {
                        inorderTreeWalk(x.left);
                        printKey(x);
                        inorderTreeWalk(x.right);
                }
        }

        public Node search(Node x, int key)
        {
                if (x.key == key || x == Tnill)
                        return x;
                else if (x.key < key)
                        return search(x.right, key);
                else
                        return search(x.left, key);
        }

        public Node min(Node x)
        {
                while (x.left != Tnill)
                        x = x.left;
                return x;
        }

        public Node max(Node x)
        {
                while (x.right != Tnill)
                        x = x.right;
                return x;
        }

        // Left Rotate
        private void leftRotate(Node x)
        {
                Node y = x.right;
                x.right = y.left;
                if (y.left != Tnill)
                        y.left.p = x;
                y.p = x.p;
                if (x.p == Tnill)
                        root = y;
                else if (x.p.left == x)
                        x.p.left = y;
                else
                        x.p.right = y;
                y.left = x;
                x.p = y;
        }

        private void rightRotate(Node y)
        {
                Node x = y.left;
                y.left = x.right;
                if (x.right != Tnill)
                        x.right.p = y;
                x.p = y.p;
                if (y.p == Tnill)
                        root = x;
                else if (y.p.left == y)
                        y.p.left = x;
                else
                        y.p.right = x;
                x.right = y;
                y.p = x;
        }

        public void RBinsert(int key)
        {
                Node y = Tnill;
                Node x = root;
                Node z = new Node(key,"RED",Tnill,Tnill,Tnill);
                while (x != Tnill)
                {
                        y = x;
                        if (z.key < x.key)
                                x = x.left;
                        else
                                x = x.right;
                }
                z.p = y;
                if (y == Tnill)
                        root = z;
                else if (z.key < y.key)
                        y.left = z;
                else
                        y.right = z;
                RBinsertFix(z);
        }

        // Fix the tree after insertion, complated, please read Ch 13 in the Reference book
        private void RBinsertFix(Node z)
        {
                while(z.p.color.equals("RED"))
                {
                        if (z.p == z.p.p.left)
                        {
                                Node y = z.p.p.right; // Uncle
                                if (y.color.equals("RED"))
                                {
                                        z.p.color = "BLACK";
                                        y.color = "BLACK";
                                        z.p.p.color = "RED";
                                        z = z.p.p;
                                }
                                else 
                                {
                                        if (z == z.p.right)
                                        {
                                                z = z.p;
                                                leftRotate(z);
                                        }
                                        z.p.color = "BLACK";
                                        z.p.p.color = "RED";
                                        rightRotate(z.p.p);
                                }
                        }else
                        {
                                Node y = z.p.p.left; // Uncle
                                if (y.color.equals("RED"))
                                {
                                        z.p.color = "BLACK";
                                        y.color = "BLACK";
                                        z.p.p.color = "RED";
                                        z = z.p.p;
                                }
                                else 
                                {
                                        if (z == z.p.left)
                                        {
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

        private void RBtransplant(Node u, Node v)
        {
                if (u.p == Tnill)
                        root = v;
                else if (u.p.left == u)
                        u.p.left = v;
                else
                        u.p.right = v;
                v.p = u.p;
        }

        public void delete(int key)
        {
                Node x = search(root, key);
                if (x == Tnill)
                        System.out.println("Sorry, can not delete since the tree does not contain this node, dumbass");
                else
                        delete(x);
        }
        
        // Delete a node in the tree
        private void delete(Node z)
        {
                Node y = z;
                Node x;
                String y_o_color = y.color;
                if (z.left == Tnill)
                {
                        x = z.right;
                        RBtransplant(z, z.right);
                }else if (z.right == Tnill)
                {
                        x = z.left;
                        RBtransplant(z, z.left);
                }else
                {
                        y = min(z.right);
                        y_o_color = y.color;
                        x = y.right;
                        if (y.p == z)
                                x.p = y;
                        else
                        {
                                RBtransplant(y, y.right);
                                y.right = z.right;
                                y.right.p = y;
                        }
                        RBtransplant(z, y);
                        y.left = z.left;
                        y.left.p = y;
                        y.color = z.color;
                }
                if (y_o_color.equals("BLACK"))
                        RBdeleteFix(x);
        }

        // Fix the Red-Black property, please read Ch 13 from <Introduction to Algorithm>
        private void RBdeleteFix(Node x)
        {
                while (x != root && x.color.equals("BLACK"))
                {
                        if (x == x.p.left)
                        {
                                Node w = x.p.right;
                                if (w.color.equals("RED"))
                                {
                                        w.color = "BLACK";
                                        x.p.color = "RED";
                                        leftRotate(x.p);
                                        w = x.p.right;
                                }
                                if (w.left.color.equals("BLACK") && w.right.color.equals("BLACK"))
                                {
                                        w.color = "RED";
                                        x = x.p;
                                }
                                else 
                                {
                                        if (w.right.color.equals("BLACK"))
                                        {
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
                        }
                        else
                        {
                                Node w = x.p.left;
                                if (w.color.equals("RED"))
                                {
                                        w.color = "BLACK";
                                        x.p.color = "RED";
                                        rightRotate(x.p);
                                        w = x.p.left;
                                }
                                if (w.left.color.equals("BLACK") && w.right.color.equals("BLACK"))
                                {
                                        w.color = "RED";
                                        x = x.p;
                                }
                                else 
                                {
                                        if (w.left.color.equals("BLACK"))
                                        {
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
