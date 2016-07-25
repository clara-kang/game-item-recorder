package ui;

import com.google.inject.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IndexFrame extends Frame {

    public static IndexFrame awtGraphicsDemo;
    public static Injector injector;

    public IndexFrame(){
        super("Blade&Soul Game Item Recorder");
        prepareGUI();
    }

    public static void main(String[] args){
        awtGraphicsDemo = new IndexFrame();
        injector = Guice.createInjector(new IndexFrameModule());
        awtGraphicsDemo.add(injector.getInstance(IndexPanel.class));
        awtGraphicsDemo.setVisible(true);
    }

    private void prepareGUI(){
        setSize(400,400);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        ImageIcon img = new ImageIcon(getClass().getResource("icon.png"));
        setIconImage(img.getImage());
    }

    public void switchComponent(Class<? extends JComponent> componentToRemove, Class<? extends JComponent> componentToAdd) {
        remove(injector.getInstance(componentToRemove));
        add(injector.getInstance(componentToAdd));
        revalidate();
        repaint();
    }

    public void switchComponent(JComponent componentToRemove, Class<? extends JComponent> componentToAdd) {
        remove(componentToRemove);
        add(injector.getInstance(componentToAdd));
        revalidate();
        repaint();
    }

    public void switchComponent(Class<? extends JComponent> componentToRemove, JComponent componentToAdd) {
        remove(injector.getInstance(componentToRemove));
        add(componentToAdd);
        revalidate();
        repaint();
    }

    public void switchComponent(JComponent componentToRemove, JComponent componentToAdd) {
        remove(componentToRemove);
        add(componentToAdd);
        revalidate();
        repaint();
    }

    public static class IndexFrameModule extends AbstractModule {

        @Provides
        IndexFrame provideIndexFrame() {
            return awtGraphicsDemo;
        }

        protected void configure(){
        }
    }

}
