package structures;

import javax.sound.midi.SysexMessage;
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
        	root = new TagNode(sc.nextLine().replaceAll("[<>]", ""), null, null);

        	Stack<TagNode> openNodes = new Stack<>();
        	openNodes.push(root);

        	TagNode ptr = root;

        	while(sc.hasNextLine()) {
        		String str = sc.nextLine();

        		if(str.contains("<") && str.charAt(1) != '/') {
        			// find first non-null node of relevant parent
					TagNode prev = openNodes.peek();

					if(prev.firstChild == null) {
						prev.firstChild = new TagNode(str.replaceAll("[<>]", ""), null, null);
						ptr = prev.firstChild;
					} else {
						prev = prev.firstChild;

						while(prev.sibling != null)
							prev = prev.sibling;

						prev.sibling = new TagNode(str.replaceAll("[<>]", ""), null, null);
						ptr = prev.sibling;
					}

					openNodes.push(ptr);
        		} else if(str.contains("<")){
        			openNodes.pop();

        			if(openNodes.size() > 0)
        				ptr = openNodes.peek();
        			else
        				break;
        		} else {
        			if(ptr.firstChild == null)
        				ptr.firstChild = new TagNode(str, null, null);
        			else {
        				ptr = ptr.firstChild;

        				while(ptr.sibling != null)
							ptr = ptr.sibling;

        				ptr.sibling = new TagNode(str, null, null);
					}
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

	private void recReplaceTag(TagNode parent, String oldTag, String newTag) {
		if(parent.tag.equals(oldTag))
			parent.tag = newTag;

		while(parent != null) {
			TagNode ptr = parent.firstChild;

			while(ptr != null) {
				recReplaceTag(ptr, oldTag, newTag);
				ptr = ptr.sibling;
			}

			parent = parent.sibling;
		}
	}

	public void replaceTag(String oldTag, String newTag) {
		recReplaceTag(root, oldTag, newTag);
	}
	
	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 * 
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */

	private void recBoldRow(TagNode parent, int row) {
		if(parent == null)
			return;
		else if(parent.tag.equals("table")) {
			TagNode ptr = parent.firstChild;

			for(int count = 1; count < row; count++) {
				ptr = ptr.sibling;
			}

			// do first col w/o loop
			TagNode fbr = new TagNode("br", ptr.firstChild, ptr.firstChild.sibling);
			ptr.firstChild = fbr;
		    ptr = ptr.firstChild;

			ptr.firstChild.sibling = null;

			while(ptr.sibling != null) {
				TagNode br =  new TagNode("br", ptr.sibling, ptr.sibling.sibling);
				br.firstChild.sibling = null;

				ptr.sibling = br;
				ptr = ptr.sibling;
			}

		} else {
			TagNode ptr = parent.firstChild;

			while(ptr != null) {
				recBoldRow(ptr, row);
				ptr = ptr.sibling;
			}
		}
	}

	public void boldRow(int row) {
	    TagNode node = new TagNode(null, root, null);
		recBoldRow(node, row);
	}
	
	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and, 
	 * in addition, all the li tags immediately under the removed tag are converted to p tags. 
	 * 
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */

	// in each step, look at node's children
	private void recRemoveTag(TagNode parent, String tag) {
	    TagNode table = parent.firstChild;

	    while(table != null) {
            if (table.tag.equals(tag)) {
                TagNode ptr = table.firstChild, first = ptr;

                while (ptr != null) {
                    if (tag.equals("ol") || tag.equals("ul"))
                        ptr.tag = "p";

                    if (ptr.sibling == null)
                        break;

                    ptr = ptr.sibling;
                }

                ptr.sibling = parent.firstChild.sibling;
                parent.firstChild = first;

            } else {
                recRemoveTag(table, tag);
            }

            table = table.sibling;
        }
    }

	public void removeTag(String tag) {
	    TagNode node = new TagNode(null, root, null);

        recRemoveTag(node, tag);
	}
	
	/**
	 * Adds a tag around all occurrences of a word in the DOM tree.
	 * 
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */

	private void recAddTag(TagNode parent, String word, String tag) {
	    TagNode ptr = parent.firstChild;

	    if(ptr.tag.equals(word))
	        ptr = new TagNode(tag, ptr, ptr.sibling);

	    while(ptr != null) {
	        if(ptr.tag.equals(word))
	            ptr = new TagNode(tag, ptr, ptr.sibling);
            else
	            recAddTag(ptr, word, tag);
        }
    }

	public void addTag(String word, String tag) {
        recAddTag(root, word, tag);
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
