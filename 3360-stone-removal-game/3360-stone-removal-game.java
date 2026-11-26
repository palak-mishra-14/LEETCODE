
class Solution {
    public boolean canAliceWin(int n) {
        int remove = 10;
        boolean alice = true;

        while (n >= remove) {
            n -= remove;
            remove--;
            alice = !alice;
        }

        return !alice;
    }
}
