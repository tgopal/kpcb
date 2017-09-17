/*
 * Author: @tgopal - Tejas Gopal, KPCB Engineering Fellow Applicant
 *
 * Class PrimHashmap defines a custom implementation for a fized-size Hashmap
 * using only primitive types. The map will support the following functions:
 *
 * constructor (size) - Initializes new hashmap
 * set(key, val) - Maps a String key to an ArbObj val
 * get(key) - Retrieves the ArbObj that this String key maps to
 * delete(key) - deletes the key-val mapping
 * load() - finds the load factor, a measure of how full the map is
 */
public class PrimHashmap<ArbObj> {

    private int hashmapSize; // The true size of the hashmap - underlying array length
    private Object[] table;  // Internal data structure: array to hold objects
    private int numItems;    // Number of items currently in hashmap
    private int maxCapacity; // max capacity as specified by user

    /**
     * Constructor PrimHashmap initializes all fields to 0 or null if no size is specified,
     * and waits for the constructor(size) function to be called on the object for any
     * operations to be performed.
     */
    public PrimHashmap() {
        this.hashmapSize = 0;
        this.table = null;
        this.numItems = 0;
        this.maxCapacity = 0;
    }

    /**
     * Constructor PrimHashmap(sz) initializes all fields given the user-specified max
     * capacity of the hashmap. 
     *
     * @param size
     *  Specifies the max capacity of the hashmap to be created.
     */
    public PrimHashmap(int size) {
        hashmapSize = findNextPowerOfTwo(size);
        table = new Object[hashmapSize];
        numItems = 0;
        maxCapacity = size;
    }

    /**
     * Function constructor() will call the PrimHashmap constructor taking size as a parameter,
     * which specified the max capacity of the hashmap to be created.
     *
     * @param size
     *      Specifies max capacity of hashmap to be created.
     *
     * @return a PrimHashmap object with specified size
     */
    public final PrimHashmap<ArbObj> constructor(int size) {
        return new PrimHashmap<ArbObj>(size);
    }

    /**
     * Function set will map a String to an arbitrary object value, and put the pair within
     * the hashmap for the user to access later.
     *
     * @param key
     *      String to use as key.
     * @param value
     *      value that the key should map to, arbitrary object of user's choice
     *
     * @return true on successful set, false on unsuccessful set
     */
    public boolean set(String key, ArbObj value) {
        if (table == null) return false;

        // Find index in table to insert key-value pair
        int hashVal = key.hashCode();
        int putIndex = findHashIndex(hashVal);

        NodeList list = null;

        // If hash index has no list, create a new list for that index
        if (table[putIndex] == null) {

            NodeList newList = new NodeList();
            table[putIndex] = newList;

        }

        // Retrives list at hash index and checks for existence of duplicate
        list = (NodeList) table[putIndex];
        HashmapNode<ArbObj> duplicate = getNode(list, key);

        if (duplicate != null) {
            // Override value if duplicate found.
            duplicate.setValue(value);
        } else {
            // Max capacity check
            if(numItems >= maxCapacity) return false;

            // Otherwise create a new node with value and append to list.
            HashmapNode<ArbObj> newNode = new HashmapNode<ArbObj>(key, value);
            list.append(newNode);
            numItems++;
        }

        return true;
    }

    /**
     * Function getNumItems() is a getter function for the number of items 
     * currently in the hashmap (occupancy).
     *
     * @return number of items in hashmap.
     */
    public int getNumItems() {
        return this.numItems;
    }

    /**
     * Function getHashmapSize() is a getter function for the max capacity
     * of this hashmap.
     *
     * @return user-specified max capacity of current hashmap.
     */
    public int getHashmapSize() {
        return this.maxCapacity;
    }

    /**
     * Function getTrueSize() is a getter function for the true size
     * (closest power of 2) of the underling table of the hashmap. This
     * is used for test purposes!
     * 
     * @return true size of hashmap.
     */
    public int getTrueSize() {
        return this.table.length;
    }

    /**
     * Function getTable() returns the underlying table array of the hashmap
     * This is used for test purposes!
     * 
     * @return underlying array of hashmap.
     */
    public Object[] getTable() {
        return this.table;
    }

    /**
     * Helper function for both get() and delete() to find the HashmapNode that 
     * contains a specified key in a specified list (position in table).
     *
     * @param list
     *      Reference to the NodeList head where this key was hashed to.
     * @param key
     *      The value of the key whose value to find within a HashmapNode.
     *
     * @return the HashmapNode object storing the value originating from key.
     *
     */
    private HashmapNode<ArbObj> getNode(NodeList list, String key) {
        if (list == null) return null;

        // Retrieves head of list
        HashmapNode<ArbObj> iter = list.getFirst();

        // Iterates through list and finds value with this origin key
        while (iter != null) {
            if (iter.getOriginKey().equals(key)) {
                return iter;
            }

            iter = iter.next();
        }

        return null;
    }

    /**
     * Function get will retrieve the arbitrary object associated with a given key.
     *
     * @param key
     *      Key whose value is to be retrieved.
     *
     * @return The value associated with the specified key, or null if no such value
     *         exists for this particular key.
     * 
     */
    public ArbObj get(String key) {
        if (table == null) return null;

        // Retrieve hash index 
        int hashVal = key.hashCode();
        int putIndex = findHashIndex(hashVal);

        // Entry in table is null, so key-value cannot possibly exist
        if (table[putIndex] == null) return null;

        // Checks for existence of node and returns value
        HashmapNode<ArbObj> target = getNode((NodeList) table[putIndex], key);
        if (target == null) return null;

        return target.getValue();
    }

    /**
     * Function get will retrieve the arbitrary object associated with a given key.
     *
     * @param key
     *      Key for which the key-value entry in the map is to be deleted.
     *
     * @return The value associated with the specified key on succesful delete,
     *         or null if there was no such key-value pair to begin with.
     * 
     */
    public ArbObj delete(String key) {
        if (table == null) return null;

        // Retrieves hash index
        int hashVal = key.hashCode();
        int putIndex = findHashIndex(hashVal);

        // Entry in table is null, so key-value cannot possibly exist, no deletion 
        if (table[putIndex] == null) return null;

        // Otherwise find node and delete from list, returning deleted node's value
        NodeList list = (NodeList) table[putIndex];

        HashmapNode<ArbObj> target = getNode(list, key);
        if (target == null) return null;

        list.deleteNode(target);
        return target.getValue();   
    }

    /**
     * Function findHashIndex will return a unique index in the table for a hashCode value.
     *
     * @param hashVal
     *      A String's hashCode() value.
     *
     * @return The index in the underlying table where this string will be hashed.
     * 
     */
    private int findHashIndex(int hashVal) {
        return hashVal & (table.length-1);
    }

    /**
     * Function load will return the load factor (numItems/capacity) of the hashmap.
     *
     * @return Load factor as described above in float format.
     * 
     */
    public float load() {
        if (table == null || maxCapacity == 0) {
            return 0;
        }
        float loadFactor = (numItems / (float) maxCapacity);
        return loadFactor;
    }

    /**
     * Function findNextPowerOfTwo will find the closest power of 2 to an integer.
     * If the integer itself is a power of two, the original integer will be returned.
     *
     * @param paramSize
     *      The integer to find the closest power of two for.
     *
     * @return int closest power of two to the paramSize
     * 
     */
    private int findNextPowerOfTwo(int paramSize) {
        return (int) Math.pow(2, Math.ceil(Math.log(paramSize) / Math.log(2)));
    }

    /**
     * Class NodeList will serve as the LinkedList connections in each of the underlying 
     * table indices of the hashmap. This linked list design is how the map avoids
     * collisions. It consists of HashmapNodes, defined after this class. This will be
     * a singly linked list.
     *
     * Will support the following operations:
     *  append a HashmapNode
     *  delete a HashmapNode
     *  get the first HashmapNode in list
     *  get the last HashmapNode in list
     */
    private class NodeList {
        private HashmapNode front;  // leading node in the linked list
        private HashmapNode back;   // tail node in the linked list
        private int size;           // number of nodes in the list

        /**
         * Constructor initializes all member variables.
         */
        public NodeList() {
            this.front = null;
            this.back = null;
            this.size = 0;
        }

        /**
         * Function append will append a node to the end of the list.
         *
         * @param node
         *      Reference to the node to add to the end of this list.
         *
         */
        private void append(HashmapNode node) {

            if (front == null) {
                // If first node, create front and back
                front = node;
                back = node;
            } else {
                // Else, append to tail.
                back.setNext(node);
                node.prevNode = back;
                back = node;
            }

            size++;
        }

        /**
         * Function deleteNode will find and delete a node in the list. If it cannot
         * find the node, it will do nothing.
         *
         * @param node
         *      The reference to the node in the list to delete, if it exists.
         *
         */
        private void deleteNode(HashmapNode node) {

            if (front == null) return;

            HashmapNode iter = front;

            // Iterate through the list until the node with the same origin
            // key is found
            while (iter != null) {
                if (iter.getOriginKey().equals(node.getOriginKey())) {

                    // Manipulate pointers around node to delete from list
                    HashmapNode prev = iter.prev();
                    if (prev == null) {
                        front = iter.next();
                    } else {
                        prev.setNext(iter.next());
                    }
                }

                iter = iter.next();
            }
        }

        /**
         * Function getFirst will return the node at the front of the list.
         *
         * @return reference to front node.
         *
         */
        public HashmapNode getFirst() {
            return front;
        }

        /**
         * Function getLast will return the node at the back of the list.
         *
         * @return reference to back node.
         *
         */
        public HashmapNode getLast() {
            return back;
        }
    }

    /**
     * Class HashmapNode will serve as the definition for individual nodes within 
     * NodeLists in the underlying table array of the hashmap. Every value, when
     * set, will be wrapped within a HashmapNode and stored within one of the lists
     * in the table.
     *
     * Will support the following operations:
     *      getting the previous node in its list
     *      getting the next node in its list
     *      setting the next node to this one
     *      getting the String key origin of the value in this node
     *      getting the value stored in this node.
     *      setting the value stored in this node.
     */
    private class HashmapNode<ArbObj> {

        private HashmapNode nextNode;   // reference to next node in list
        private HashmapNode prevNode;   // reference to prev node in list
        private String fromKey;         // origin key for this value
        private ArbObj value;           // value stored in the node.

        /**
         * Constructor HashmapNode will initialize all member variables.
         *
         * @param fromKey 
         *      The origin key for the value stored in this node.
         * @param value
         *      The value to be stored in this hashmap node.
         */
        public HashmapNode(String fromKey, ArbObj value) {
            this.fromKey = fromKey;
            this.value = value;
            this.nextNode = null;
            this.prevNode = null;
        }

        /**
         * Function prev() returns the previous node in the list
         *
         * @return reference to previous node in the list, or null if no prev.
         */
        public HashmapNode prev() {
            return this.prevNode;
        }

        /**
         * Function next() returns the next node in the list
         *
         * @return reference to next node in the list, or null if no next.
         */
        public HashmapNode next() {
            return this.nextNode;
        }

        /**
         * Function setNext sets the next node to the current node in the list.
         *
         * @param next
         *      Reference to the HashmapNode to set as the next node in the list.
         */
        public void setNext(HashmapNode next) {
            this.nextNode = next;
        }

        /**
         * Function getOriginKey returns the origin key for the value stored in this node.
         *
         * @return String key for the value stored in the node.
         */
        public String getOriginKey() {
            return this.fromKey;
        }

        /**
         * Function getValue() returns the value stored in this node.
         *
         * @return ArbObj value stored in the node.
         */
        public ArbObj getValue() {
            return this.value;
        }

        /**
         * Function setValue sets the value to be held in the current node.
         *
         * @param value
         *      Reference to the arbitrary object value to be held in this node.
         */
        public void setValue(ArbObj value) {
            this.value = value;
        }
    }
}
