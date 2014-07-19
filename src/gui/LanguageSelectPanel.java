
package gui;

import game.settings.Settings;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Sergio Balbuena (Sbalbp) <sbalbp@gmail.com>
 */
public class LanguageSelectPanel extends JPanel implements KeyInteractive{
    
    private int width = 700, height = 700, rows = 3, columns = 3, buttonW = 128, buttonH = 128;
    private int currentSlot = 0, nextSlot = 0, currentPage = 0, lastPage;
    private boolean firstTime = true;
    private String[] languages = {"en","es"};
    private JButton[] buttons;
    private JButton backButton, leftButton, rightButton;
    private LanguageSelectPanelButtonPressed buttonListener = new LanguageSelectPanelButtonPressed();
    private LanguageSelectPanelMouseEvent mouseListener = new LanguageSelectPanelMouseEvent();
    
    public LanguageSelectPanel(){
        super();
        
        this.setLayout(null);
        this.setBounds(0,0,width,height);
        
        this.setBackground(Color.BLACK);
        this.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        
        lastPage = (int)Math.ceil(((double)languages.length/(rows*columns))-1);

        buttons = new JButton[Math.min(rows*columns,languages.length)];
        for(int i=0; i<buttons.length; i++){
            buttons[i] = new JButton();
            buttons[i].setBorder(javax.swing.BorderFactory.createLineBorder(Color.red));
            buttons[i].setBorderPainted(false);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setActionCommand(""+i);
            buttons[i].addActionListener(buttonListener);
            buttons[i].setName(""+i);
            buttons[i].addMouseListener(mouseListener);
            this.add(buttons[i]);
        }
        
        leftButton = new JButton();
        leftButton.setBorderPainted(false);
        leftButton.setContentAreaFilled(false);
        leftButton.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/arrow_left_0.gif"));
        leftButton.setRolloverIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/arrow_left_0_rollover.gif"));
        leftButton.setRolloverEnabled(true);
        leftButton.setActionCommand("left");
        leftButton.addActionListener(buttonListener);
        this.add(leftButton);
        
        rightButton = new JButton();
        rightButton.setBorderPainted(false);
        rightButton.setContentAreaFilled(false);
        rightButton.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/arrow_right_0.gif"));
        rightButton.setRolloverIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/arrow_right_0_rollover.gif"));
        rightButton.setRolloverEnabled(true);
        rightButton.setActionCommand("right");
        rightButton.addActionListener(buttonListener);
        this.add(rightButton);

        backButton = new JButton();
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/back_0.gif"));
        backButton.setRolloverIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/back_0_rollover.gif"));
        backButton.setRolloverEnabled(true);
        backButton.setBounds(width-120,height-80,100,60);
        this.add(backButton);
        
        updateButtons();
        updatePage();
        update();
    }

    private int getNumberButtonsCurrentPage(){
        return currentPage == lastPage ? (languages.length-1)%(rows*columns)+1 : rows*columns;
    }
    
    private void updateButtons(){
        int xInc, yInc, xIni = 100, buttonsPerPage = rows*columns, yReturn;
        
        if(firstTime){
            yReturn = 100;
            firstTime = false;
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
            buttons[i].setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/languages/"+languages[j]+".png"));
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
            backButton.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/back_0.gif"));
        }
        else{
            buttons[currentSlot].setBorderPainted(false);
        }
        currentSlot = nextSlot;
        if(currentSlot == rows*columns){
            backButton.setIcon(new ImageIcon(Settings.get("assets.image.route")+"/icons/back_0_rollover.gif"));
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
        
    }
    
    public void back(){
        
    }
    
    private class LanguageSelectPanelButtonPressed implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            switch(e.getActionCommand()){
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
                    System.out.println(e.getActionCommand());
            }
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
