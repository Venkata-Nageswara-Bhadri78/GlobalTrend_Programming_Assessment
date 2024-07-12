import java.util.Stack;
public class ValidParentheses {
    public boolean isValid(String s) {
        // Create a stack to hold the opening brackets
        Stack<Character> stack = new Stack<>();
        // Iterate through each character in the string
        for (char c : s.toCharArray()) {
            // Push opening brackets onto the stack
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            }
            // Check closing brackets
            else if (c == ')' || c == '}' || c == ']') {
                // If stack is empty or the top of the stack does not match
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                if ((c == ')' && top != '(') ||
                    (c == '}' && top != '{') ||
                    (c == ']' && top != '[')) {
                    return false;
                }
            }
        }
        // If stack is empty, all opening brackets were matched
        return stack.isEmpty();
    }
    public static void main(String[] args) {
        ValidParentheses vp = new ValidParentheses();
        System.out.println(vp.isValid("()"));         // true
        System.out.println(vp.isValid("()[]{}"));     // true
        System.out.println(vp.isValid("(]"));         // false
        System.out.println(vp.isValid("([)]"));       // false
        System.out.println(vp.isValid("{[]}"));       // true
    }
}
