package hangman;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class TextLimitDocumentFilter extends DocumentFilter {
	private int limit;
	
	public TextLimitDocumentFilter(int limit) {
		this.limit = limit;
	}
	
	@Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if(fb.getDocument().getLength() >= limit)
            super.replace(fb, 0, limit, text, attrs); 
        else {
			super.replace(fb, offset, length, text, attrs);
		}
    }
}
