
package org.cs.snake;

class KeyQueue {

    public final static int NOKEY = 0,
            LEFT = 1,
            RIGHT = 2,
            UP = 3,
            DOWN = 4;

    int queue[], used, head, tail;

    public KeyQueue(int length) {
        queue = new int[length];
        used = head = tail = 0;
    }

    public synchronized void putKey(int key) {
        if (used < queue.length) {
            queue[head++] = key;
            if (head == queue.length) {
                head = 0;
            }
            ++used;
        }
    }

    public synchronized int getKey() {
        if (used > 0) {
            int key = queue[tail++];
            if (tail == queue.length) {
                tail = 0;
            }
            --used;
            return key;
        }
        return NOKEY;
    }

    public synchronized void clear() {
        used = head = tail = 0;
    }

} // class KeyQueue


