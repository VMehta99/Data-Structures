package structures;

import java.util.*;

/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 * 
 */
public class Tree {
	
	/**
	 * Root node
	 */
	public TagNode root=null;
	
	/**
	 * Scanner used to read input HTML file when building the tree
	 */
	public Scanner sc;
	
	/**
	 * Initializes this tree object with scanner for input HTML file
	 uu*
	 * @param sc scanner for input html file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}

	/**
	 * builds the dom tree from input html file, through scanner passed
	 * in to the constructor and stored in the sc field of this object.
	 *
	 * the root of the tree that is built is referenced by the root field of this object.
	 */

	public void build() {
        if(!sc.hasNextLine()) {
            root = new TagNode("", null, null);
        } else {
            // do first node separately - java null implementation does not hold
            String s = sc.nextLine();
            root = new TagNode(s.replaceAll("[<>]", ""), null, null);

            TagNode ptr = (s.contains("<")) ? root.firstChild : root.sibling, lastOpen = root;

            while(sc.hasNextLine()) {
                String str = sc.nextLine();

                if(str.charAt(0) == '<') {
                    // check open-tag vs. close-tag

                    if(str.charAt(1) == '\\') {
                        ptr = lastOpen.sibling;
                    } else {
                        ptr = new TagNode(str.replaceAll("[<>]", ""), null, null);

                        // hopefully shallow-copies ptr into lastOpen
                        lastOpen = ptr;
                        ptr = ptr.firstChild;
                    }

                } else {
                    ptr = new TagNode(str, null, null);
                    ptr = ptr.sibling;
                }
            }
        }
    }

	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 * 
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */
	public void replaceTag(String oldTag, String newTag) {
		/** COMPLETE THIS METHOD **/
	}
	
	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 * 
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */
	public void boldRow(int row) {
		/** COMPLETE THIS METHOD **/
	}
	
	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and, 
	 * in addition, all the li tags immediately under the removed tag are converted to p tags. 
	 * 
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */
	public void removeTag(String tag) {
		/** COMPLETE THIS METHOD **/
	}
	
	/**
	 * Adds a tag around all occurrences of a word in the DOM tree.
	 * 
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */
	public void addTag(String word, String tag) {
		/** COMPLETE THIS METHOD **/
	}
	
	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 * 
	 * @return HTML string, including new lines. 
	 */
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}
	
	private void getHTML(TagNode root, StringBuilder sb) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if (ptr.firstChild == null) {
				sb.append(ptr.tag);
				sb.append("\n");
			} else {
				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");	
			}
		}
	}
	
	/**
	 * Prints the DOM tree. 
	 *
	 */
	public void print() {
		print(root, 1);
	}
	
	private void print(TagNode root, int level) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			};
			if (root != this.root) {
				System.out.print("|---- ");
			} else {
				System.out.print("      ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
	}
}
