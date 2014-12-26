
package gui;

import game.settings.Settings;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import utils.AssetsManager;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class LanguageSelectPanel extends JPanel implements KeyInteractive{
    
    private int width = 730, height = 700, rows = 3, columns = 3, buttonW = 128, buttonH = 128;
    private int currentSlot = 0, nextSlot = 0, currentPage = 0, lastPage;
    private String[] languages = {"en","es","fr","ge","es","fr","ge","fr","ge","es","fr","ge","en"};
    private JButton[] buttons;
    private JButton backButton, leftButton, rightButton;
    private ActionListener listener;
    private LanguageSelectPanelMouseEvent mouseListener = new LanguageSelectPanelMouseEvent();
    
    public LanguageSelectPanel(ActionListener buttonListener){
        super();
        
        this.setLayout(null);
        this.setBounds(0,0,width,height);
        
        this.setBackground(Color.BLACK);
        this.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        
        listener = buttonListener;
        
        lastPage = (int)Math.ceil(((double)languages.length/(rows*columns))-1);

        buttons = new JButton[Math.min(rows*columns,languages.length)];
        for(int i=0; i<buttons.length; i++){
            buttons[i] = new JButton();
            buttons[i].setBorder(javax.swing.BorderFactory.createLineBorder(Color.red));
            buttons[i].setBorderPainted(false);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setActionCommand("languageSelectPanel."+i);
            buttons[i].addActionListener(listener);
            buttons[i].setName(""+i);
            buttons[i].addMouseListener(mouseListener);
            this.add(buttons[i]);
        }
        
        leftButton = new JButton();
        leftButton.setBorderPainted(false);
        leftButton.setContentAreaFilled(false);
        leftButton.setIcon(AssetsManager.getImageIcon("/icons/arrow_left_0", "gif"));
        leftButton.setRolloverIcon(AssetsManager.getImageIcon("/icons/arrow_left_0_rollover", "gif"));
        leftButton.setRolloverEnabled(true);
        leftButton.setActionCommand("languageSelectPanel.left");
        leftButton.addActionListener(listener);
        this.add(leftButton);
        
        rightButton = new JButton();
        rightButton.setBorderPainted(false);
        rightButton.setContentAreaFilled(false);
        rightButton.setIcon(AssetsManager.getImageIcon("/icons/arrow_right_0", "gif"));
        rightButton.setRolloverIcon(AssetsManager.getImageIcon("/icons/arrow_right_0_rollover", "gif"));
        rightButton.setRolloverEnabled(true);
        rightButton.setActionCommand("languageSelectPanel.right");
        rightButton.addActionListener(listener);
        this.add(rightButton);

        backButton = new JButton();
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setIcon(AssetsManager.getImageIcon("/icons/back_0", "gif"));
        backButton.setRolloverIcon(AssetsManager.getImageIcon("/icons/back_0_rollover", "gif"));
        backButton.setRolloverEnabled(true);
        backButton.setBounds(width-120,height-80,100,60);
        backButton.setActionCommand("languageSelectPanel.back");
        backButton.addActionListener(listener);
        this.add(backButton);
        
        updatePage();
        update();
    }
    
    public String getLanguage(int index){
        return languages[index+rows*columns*currentPage];
    }

    private int getNumberButtonsCurrentPage(){
        return currentPage == lastPage ? (languages.length-1)%(rows*columns)+1 : rows*columns;
    }
    
    private void updateButtons(){
        int xInc, yInc, xIni = 100, buttonsPerPage = rows*columns, yReturn;
        
        if(Settings.get("language_set").equals("yes")){
            yReturn = 100;
            backButton.setVisible(true);
        }
        else{
            yReturn = 0;
            backButton.setVisible(false);
        }
        
        leftButton.setBounds(10,(height-80-yReturn)/2,80,80);
        rightButton.setBounds(width-90,(height-80-yReturn)/2,80,80);
        
        xInc = ((width-(2*xIni))-columns*buttonW)/(columns+1);
        yInc = ((height-yReturn)-rows*buttonH)/(rows+1);
        
        for(int i=0; i<buttons.length; i++){
            int j = i%buttonsPerPage;
            buttons[j].setBounds(xIni+xInc+(xInc+buttonW)*(j%columns),yInc+(yInc+buttonH)*(j/columns),buttonW,buttonH); 
        }
    }
    
    private void updatePage(){
        int i, j, buttonsCurrentPage = getNumberButtonsCurrentPage();
        
        for(i=0, j=currentPage*rows*columns; i<buttonsCurrentPage; i++, j++){
            buttons[i].setVisible(true);
            buttons[i].setIcon(AssetsManager.getImageIcon("/icons/languages/"+languages[j], "png"));
        }
        for(;i<buttons.length; i++){
            buttons[i].setVisible(false);
        }
        
        if (currentPage == 0){
            leftButton.setVisible(false);
        }
        else{
            leftButton.setVisible(true);
        }
        
        if (currentPage == lastPage){
            rightButton.setVisible(false);
        }
        else{
            rightButton.setVisible(true);
        }
    }
    
    private void update(){
        if(currentSlot == rows*columns){
            backButton.setIcon(AssetsManager.getImageIcon("/icons/back_0", "gif"));
        }
        else{
            buttons[currentSlot].setBorderPainted(false);
        }
        currentSlot = nextSlot;
        if(currentSlot == rows*columns){
            backButton.setIcon(AssetsManager.getImageIcon("/icons/back_0_rollover", "gif"));
        }
        else{
            buttons[currentSlot].setBorderPainted(true);
        }
    }
    
    private void previousPage(){
        currentPage = currentPage == 0 ? currentPage : currentPage-1;
        updatePage();
    }
    
    private void nextPage(){
        currentPage = currentPage == lastPage ? lastPage : currentPage+1;
        updatePage();
    }
    
    public void up(){
        int buttonsCurrentPage = getNumberButtonsCurrentPage();
        
        if(currentSlot == rows*columns){
            nextSlot = buttonsCurrentPage-1;
        }
        else{
            if(currentSlot >= columns){
                nextSlot = currentSlot-columns;
            }
        }
        update();
    }
    
    public void left(){
        if(currentSlot != rows*columns){
            if(currentSlot%columns == 0){
                if(currentPage != 0){
                    nextSlot = currentSlot+columns-1;
                    previousPage();
                }
            }
            else{
                nextSlot = currentSlot-1;
            }
        }
        update();
    }
    
    public void right(){
        int buttonsCurrentPage = getNumberButtonsCurrentPage();

        if(currentSlot != rows*columns){
            if(currentSlot%columns == columns-1 || (currentPage == lastPage && currentSlot == buttonsCurrentPage-1)){
                if(currentPage != lastPage){
                    if((rows*columns)*(currentPage+1)+(currentSlot-columns+1) >= languages.length){
                        nextSlot = 0;
                    }
                    else{
                        nextSlot = currentSlot-columns+1;
                    }
                    nextPage();
                }
            }
            else{
                nextSlot = currentSlot+1;
            }
        }
        update();
    }
    
    public void down(){
        int buttonsCurrentPage = getNumberButtonsCurrentPage();
        
        if(currentSlot+columns >= buttonsCurrentPage){
            if(backButton.isVisible()){
                nextSlot = rows*columns;
            }
        }
        else{
            nextSlot = currentSlot+columns;
        }
        update();
    }
    
    public void select(){
        if(currentSlot < rows*columns){
            listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, buttons[currentSlot].getActionCommand()));
        }
        else{
            listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, backButton.getActionCommand()));
        }
    }
    
    public void back(){
        listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, backButton.getActionCommand()));
    }
    
    public void switchTab(){
        
    }
    
    @Override
    public void setVisible(boolean flag){
        super.setVisible(flag);
        if(flag){
            updateButtons();
        }
    }
    
    public void buttonPressed(String event){
        event = event.replace("languageSelectPanel.","");
        
        switch(event){
            case "left":
                previousPage();
                break;
            case "right":
                nextPage();
                if((rows*columns)*(currentPage)+currentSlot >= languages.length){
                    nextSlot = 0;
                    update();
                }
                break;
            default:
                System.out.println(getLanguage(Integer.parseInt(event)));//Integer.parseInt(event)+rows*columns*currentPage);
        }
    }
    
    private class LanguageSelectPanelMouseEvent implements MouseListener {
        public void mousePressed(MouseEvent ev) {
        }

        public void mouseReleased(MouseEvent ev) {
            
        }

        public void mouseEntered(MouseEvent ev) {
            
            try{
                nextSlot = Integer.parseInt(ev.getComponent().getName());
                update();
            }
            catch(NumberFormatException e){}
        }

        public void mouseExited(MouseEvent ev) {
        }

        public void mouseClicked(MouseEvent ev) {
        }
    }
}
