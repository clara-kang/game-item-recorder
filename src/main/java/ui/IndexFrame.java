package ui;

import com.google.inject.*;
import logic.DataUtil;
import logic.H2DataUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IndexFrame extends JTabbedPane {

    public static IndexFrame awtGraphicsDemo;
    public static Injector injector;
    private static JFrame frame;

    public IndexFrame(){
        frame = new JFrame("Blade&Soul Game Item Recorder");
        prepareGUI();
    }

    public static void main(String[] args){
        awtGraphicsDemo = new IndexFrame();
        injector = Guice.createInjector(new IndexFrameModule());
        JTabbedPane tabbedPane = new JTabbedPane();
        IndexPanel indexPanel = injector.getInstance(IndexPanel.class);
        awtGraphicsDemo.addTab("Tab 1", null, indexPanel,
                "Does nothing");
        awtGraphicsDemo.add(tabbedPane);
        frame.setVisible(true);
        frame.add(awtGraphicsDemo);
    }

    private void prepareGUI(){
        frame.setSize(400,600);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        ImageIcon img = new ImageIcon(getClass().getResource("icon.png"));
        frame.setIconImage(img.getImage());
    }

    public void switchComponent(Class<? extends JComponent> componentToRemove, Class<? extends JComponent> componentToAdd) {
        remove(injector.getInstance(componentToRemove));
        insertTab("Tab 1", null, injector.getInstance(componentToAdd), "String", 0);
        setSelectedIndex(0);
        frame.revalidate();
        frame.repaint();
    }

    public void switchComponent(JComponent componentToRemove, Class<? extends JComponent> componentToAdd) {
        remove(componentToRemove);
        insertTab("Tab 1", null, injector.getInstance(componentToAdd), "String", 0);
        setSelectedIndex(0);
        frame.revalidate();
        frame.repaint();
    }

    public void switchComponent(Class<? extends JComponent> componentToRemove, JComponent componentToAdd) {
        remove(injector.getInstance(componentToRemove));
        insertTab("Tab 1", null, componentToAdd, "String", 0);
        setSelectedIndex(0);
        frame.revalidate();
        frame.repaint();
    }

    public void switchComponent(JComponent componentToRemove, JComponent componentToAdd) {
        remove(componentToRemove);
        insertTab("Tab 1", null, componentToAdd, "String", 0);
        setSelectedIndex(0);
        frame.revalidate();
        frame.repaint();
    }

    public static class IndexFrameModule extends AbstractModule {

        @Provides
        IndexFrame provideIndexFrame() {
            return awtGraphicsDemo;
        }

        protected void configure(){
            bind(DataUtil.class).to(H2DataUtil.class).in(Singleton.class);
        }
    }

}
