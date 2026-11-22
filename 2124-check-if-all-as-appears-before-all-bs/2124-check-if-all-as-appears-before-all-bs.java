
class Solution {
    public boolean checkString(String s) {
        boolean foundB = false;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'b') {
                foundB = true;
            }
            else if (foundB) {
                return false;
            }
        }

        return true;
    }
}
