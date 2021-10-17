package view.image;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResizeKeyListener implements KeyListener {
    public final char ZOOM_IN_KEY = '+';
    public final char ZOOM_OUT_KEY = '-';


    private EventCallback onZoomIn;
    private EventCallback onZoomOut;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == ZOOM_IN_KEY) {
            onZoomIn.call();
        } else if (e.getKeyChar() == ZOOM_OUT_KEY) {
            onZoomOut.call();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static ResizeKeyListener actions(EventCallback onZoomIn, EventCallback onZoomOut) {
        return new ResizeKeyListener(onZoomIn, onZoomOut);
    }
}
