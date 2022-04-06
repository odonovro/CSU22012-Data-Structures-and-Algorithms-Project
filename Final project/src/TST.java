import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TST {
    private int n;              // size
    private Node root;   // root of TST

    private static class Node {
        private char c;                        // character
        private Node left, mid, right;  // left, middle, and right subtries
        private Info val;                     // Info associated with string
    }

    /**
     * Initializes an empty string symbol table.
     */
    public TST() {
    }

    /**
     * Returns the number of key-Info pairs in this symbol table.
     * @return the number of key-Info pairs in this symbol table
     */
    public int size() {
        return n;
    }

    /**
     * Does this symbol table contain the given key?
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *     {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    /**
     * Returns the Info associated with the given key.
     * @param key the key
     * @return the Info associated with the given key if the key is in the symbol table
     *     and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Info get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with null argument");
        }
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        Node x = get(root, key, 0);
        if (x == null) return null;
        return x.val;
    }

    // return subtrie corresponding to given key
    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        char c = key.charAt(d);
        if      (c < x.c)              return get(x.left,  key, d);
        else if (c > x.c)              return get(x.right, key, d);
        else if (d < key.length() - 1) return get(x.mid,   key, d+1);
        else                           return x;
    }

    /**
     * Inserts the key-Info pair into the symbol table, overwriting the old Info
     * with the new Info if the key is already in the symbol table.
     * If the Info is {@code null}, this effectively deletes the key from the symbol table.
     * @param key the key
     * @param val the Info
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(String key, Info val) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with null key");
        }
        if (!contains(key)) n++;
        else if(val == null) n--;       // delete existing key
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Info val, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        if      (c < x.c)               x.left  = put(x.left,  key, val, d);
        else if (c > x.c)               x.right = put(x.right, key, val, d);
        else if (d < key.length() - 1)  x.mid   = put(x.mid,   key, val, d+1);
        else                            x.val   = val;
        return x;
    }

    /**
     * Returns the string in the symbol table that is the longest prefix of {@code query},
     * or {@code null}, if no such string.
     * @param query the query string
     * @return the string in the symbol table that is the longest prefix of {@code query},
     *     or {@code null} if no such string
     * @throws IllegalArgumentException if {@code query} is {@code null}
     */
    public String longestPrefixOf(String query) {
        if (query == null) {
            throw new IllegalArgumentException("calls longestPrefixOf() with null argument");
        }
        if (query.length() == 0) return null;
        int length = 0;
        Node x = root;
        int i = 0;
        while (x != null && i < query.length()) {
            char c = query.charAt(i);
            if      (c < x.c) x = x.left;
            else if (c > x.c) x = x.right;
            else {
                i++;
                if (x.val != null) length = i;
                x = x.mid;
            }
        }
        return query.substring(0, length);
    }

    /**
     * Returns all keys in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     * @return all keys in the symbol table as an {@code Iterable}
     */
    public Iterable<String> keys() {
        Queue<String> queue = new Queue<String>();
        collect(root, new StringBuilder(), queue);
        return queue;
    }

    /**
     * Returns all of the keys in the set that start with {@code prefix}.
     * @param prefix the prefix
     * @return all of the keys in the set that start with {@code prefix},
     *     as an iterable
     * @throws IllegalArgumentException if {@code prefix} is {@code null}
     */
    public Iterable<String> keysWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
        }
        Queue<String> queue = new Queue<String>();
        Node x = get(root, prefix, 0);
        if (x == null) return queue;
        if (x.val != null) queue.enqueue(prefix);
        collect(x.mid, new StringBuilder(prefix), queue);
        return queue;
    }

    // all keys in subtrie rooted at x with given prefix
    private void collect(Node x, StringBuilder prefix, Queue<String> queue) {
        if (x == null) return;
        collect(x.left,  prefix, queue);
        if (x.val != null) queue.enqueue(prefix.toString() + x.c);
        collect(x.mid,   prefix.append(x.c), queue);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, queue);
    }


    /**
     * Returns all of the keys in the symbol table that match {@code pattern},
     * where the character '.' is interpreted as a wildcard character.
     * @param pattern the pattern
     * @return all of the keys in the symbol table that match {@code pattern},
     *     as an iterable, where . is treated as a wildcard character.
     */
    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> queue = new Queue<String>();
        collect(root, new StringBuilder(), 0, pattern, queue);
        return queue;
    }
 
    private void collect(Node x, StringBuilder prefix, int i, String pattern, Queue<String> queue) {
        if (x == null) return;
        char c = pattern.charAt(i);
        if (c == '.' || c < x.c) collect(x.left, prefix, i, pattern, queue);
        if (c == '.' || c == x.c) {
            if (i == pattern.length() - 1 && x.val != null) queue.enqueue(prefix.toString() + x.c);
            if (i < pattern.length() - 1) {
                collect(x.mid, prefix.append(x.c), i+1, pattern, queue);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        if (c == '.' || c > x.c) collect(x.right, prefix, i, pattern, queue);
    }
    
    void fileToTST(File file){
		try {
			Scanner txt = new Scanner(file);
	    	String line = txt.nextLine();
    		while (txt.hasNextLine()) {
    			line = txt.nextLine();
    	    	String[] temp_string = line.split(",");
    	    	Info INFO = new Info(temp_string);
    	    	
    	    	String[] name = INFO.name.split(" ");
    	    	String temp;
    	    	int k = 0;
    	    	for (int i = 0 ; i < name.length - k; i++) {
    	    		if (name[i].compareTo("WB") == 0 || name[i].compareTo("NB") == 0 || name[i].compareTo("SB") == 0 || name[i].compareTo("EB") == 0 || name[i].compareTo("FLAGSTOP") == 0) {
    	    			temp = name[i];
    	    			for (int j = i ; j < name.length - 1; j ++) {
    	    				name[j] = name[j+1];
    	    			}
    	    			name[name.length -1] = temp;
    	    			i--;
    	    			k++;
    	    		}
    	    	}
    	    	INFO.name = "";
    	    	for(int i = 0 ; i < name.length ; i++) {
    	    		INFO.name = INFO.name + name[i] + " ";
    	    	}
    	    	this.put(INFO.name, INFO);
    		}
	    	txt.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    public Iterable<Info> infoWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("calls infoWithPrefix() with null argument");
        }
        Queue<Info> queue = new Queue<Info>();
        Node x = get(root, prefix, 0);
        if (x == null) return queue;
        if (x.val != null) queue.enqueue(x.val);
        infoWithPrefix(x.mid, queue);
        return queue;
    }

    private void infoWithPrefix(Node x, Queue<Info> queue) {
        if (x == null) return;
        infoWithPrefix(x.left, queue);
        infoWithPrefix(x.mid, queue);
        infoWithPrefix(x.right, queue);
        if (x.val != null) queue.enqueue(x.val);
    }
    
    public static void main(String args[])
    {
		TST tst = new TST();
		//System.out.println(Character.compare('e', 'h')); // -3
		
		String[] str = new String[10];
		for (int i = 0 ; i < 10 ; i++) {
			str[i] = null;
		}
		str[2] = "she";
		Info test1 = new Info(str);
		str[2] = "sells";
		Info test2 = new Info(str);
		str[2] = "sea";
		Info test3 = new Info(str);
		str[2] = "shells";
		Info test4 = new Info(str);
		str[2] = "by";
		Info test5 = new Info(str);
		str[2] = "the";
		Info test6 = new Info(str);
		str[2] = "sea";
		Info test7 = new Info(str);
		str[2] = "shore";
		Info test8 = new Info(str);
		
		String name;
		name = test1.name;
		tst.put(name, test1);
		name = test2.name;
		tst.put(name, test2);
		name = test3.name;
		tst.put(name, test3);
		name = test4.name;
		tst.put(name, test4);
		name = test5.name;
		tst.put(name, test5);
		name = test6.name;
		tst.put(name, test6);
		name = test7.name;
		tst.put(name, test7);
		name = test8.name;
		tst.put(name, test8);
        
        Scanner Scannerinput = new Scanner(System.in);
        String input;
        Node current = tst.root;
        
        String lv = " ";
        String mv = " ";
        String rv = " ";
        
        for (int i = 0; i < 2; i--) {
        	input = Scannerinput.nextLine();
        	if (input.compareTo("end") == 0) {break;}
        	
        	if (input.compareTo("l") == 0) {
        		current = current.left;
        	}
        	if (input.compareTo("r") == 0) {
        		current = current.right;
        	}
        	if (input.compareTo("m") == 0) {
        		current = current.mid;
        	}
        	if (input.compareTo(";") == 0) {
        		current = tst.root;
        	}
        	
        	if (current == tst.root) {
        		System.out.println(current.c);
        	}
        	
        	if (current.left == null) {current.left = new Node(); current.left.c = ' ';}
        	if (current.mid == null) {current.mid = new Node(); current.mid.c = ' ';}
        	if (current.right == null) {current.right = new Node(); current.right.c = ' ';}
        	
        	if (current.left.val != null) {lv = current.left.val.name;}
        	if (current.mid.val != null) {mv = current.mid.val.name;}
        	if (current.right.val != null) {rv = current.right.val.name;}
        	
        	System.out.println(current.left.c + " " + lv + "	" + current.mid.c + " " + mv + "	" + current.right.c + " " + rv);
        	
        	lv = " ";
        	mv = " ";
        	rv = " ";
        }
        
        Scannerinput.close();
        
        
        System.out.println("fin");
        
    }
}
