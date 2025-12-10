
package br.com.infox.telas;
import java.sql.*;
import javax.swing.JOptionPane;
import br.com.infox.dao.ModuloConexao;
import br.com.infox.dao.OrdemServicoDAO;
import br.com.infox.model.OrdemServico;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Tela responsável pelo gerenciamento completo de Ordens de Serviço (OS).
 * Permite emitir, pesquisar, atualizar, excluir e imprimir ordens de serviço,
 * integrando os componentes visuais com o OrdemServicoDAO e com o JasperReports.
 *
 * Funcionalidades principais:
 *  - Pesquisa de clientes para vincular à OS;
 *  - Emissão de nova OS (Orçamento ou Ordem de Serviço);
 *  - Consulta de OS existente pelo número;
 *  - Atualização dos dados da OS;
 *  - Exclusão de OS cadastrada;
 *  - Impressão da OS em relatório via JasperReports;
 *  - Preenchimento automático de campos ao selecionar cliente;
 *  - Controle do estado dos botões conforme o fluxo da tela.
 *
 * A classe utiliza a conexão com o banco fornecida pelo ModuloConexao,
 * e trabalha diretamente com a entidade OrdemServico.
 * 
 * Esta tela é parte central do sistema X-System, sendo responsável
 * pelo fluxo de trabalho principal relacionado aos atendimentos técnicos.
 *
 * @author Wanderson Santos Lemos
 */


public class TelaOs extends javax.swing.JInternalFrame {
    
    
    
    Connection conexao = null ;
    PreparedStatement pst = null ;
    ResultSet rs = null;
    
    private String tipo ;
    

    public TelaOs() {
        initComponents();
        conexao = ModuloConexao.conector();
        try {
            this.setMaximum(true); 
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
        
        
    }
    
   private void pesquisar_cliente() {
    String sql = "SELECT idcli AS Id, nomecli AS Nome, fonecli AS Fone " +
                 "FROM tbclientes " +
                 "WHERE nomecli LIKE ?";

    try {
        pst = conexao.prepareStatement(sql);
        pst.setString(1, TxtCliPesquisar.getText().trim() + "%");
        rs  = pst.executeQuery();

        TblClientes.setModel(DbUtils.resultSetToTableModel(rs));
        TblClientes.getTableHeader().repaint();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
    }
}
   
   
   private void setar_campos() {
    int linha = TblClientes.getSelectedRow();

    if (linha >= 0) {
        String id = TblClientes.getModel().getValueAt(linha, 0).toString();
        String nome = TblClientes.getModel().getValueAt(linha, 1).toString();
        String fone = TblClientes.getModel().getValueAt(linha, 2).toString();

       
        TxtCliId.setText(id);

        
        DefaultTableModel modelo = (DefaultTableModel) TblClientes.getModel();
        modelo.setRowCount(0); 
        modelo.addRow(new Object[]{id, nome, fone});

       
        TblClientes.getTableHeader().repaint();
    }
}

   
   private void emitir_os() {

    if (TxtCliId.getText().isEmpty()
            || TxtOsEquipe.getText().isEmpty()
            || TxtOsDef.getText().isEmpty()
            || CboOsSit.getSelectedItem().equals(" ")) {

        JOptionPane.showMessageDialog(null, "Preencha todos os campos Obrigatórios");
        return;
    }

    OrdemServico os = new OrdemServico();
    os.setTipo(tipo); // aquela variável da tela (OS / Orçamento)
    os.setSituacao(CboOsSit.getSelectedItem().toString());
    os.setEquipamento(TxtOsEquipe.getText());
    os.setDefeito(TxtOsDef.getText());
    os.setServico(TxtOsServ.getText());
    os.setTecnico(TxtOsTec.getText());
    os.setValor(TxtOsValor.getText().replace(",", "."));
    os.setIdCliente(TxtCliId.getText());

    OrdemServicoDAO dao = new OrdemServicoDAO();
    boolean sucesso = dao.adicionar(os);

    if (sucesso) {
        JOptionPane.showMessageDialog(null, "OS emitida com sucesso");
        recuperarOs();                 
        BtnAdicionar.setEnabled(false); 
        BtnPesquisar.setEnabled(true);
        BtnImprimir.setEnabled(true);
    } else {
        JOptionPane.showMessageDialog(null, "Erro ao emitir OS");
    }
}
   private void pesquisar_os() {

    String num_os = JOptionPane.showInputDialog("Número da OS");

    if (num_os == null || num_os.trim().isEmpty()) {
        return;
    }

    try {
        int numero = Integer.parseInt(num_os.trim());

       OrdemServicoDAO dao = new OrdemServicoDAO();
       OrdemServico os = dao.buscarPorNumero(numero);

        if (os != null) {

            TxtOs.setText(String.valueOf(os.getOs()));
            TxtData.setText(os.getData());

            String rbtTipo = os.getTipo();
            if ("OS".equalsIgnoreCase(rbtTipo)) {
                RbtOs.setSelected(true);
                tipo = "OS";
            } else {
                RbtOrc.setSelected(true);
                tipo = "Orçamento";
            }

            CboOsSit.setSelectedItem(os.getSituacao());
            TxtOsEquipe.setText(os.getEquipamento());
            TxtOsDef.setText(os.getDefeito());
            TxtOsServ.setText(os.getServico());
            TxtOsTec.setText(os.getTecnico());
            TxtOsValor.setText(os.getValor());
            TxtCliId.setText(String.valueOf(os.getIdCliente()));

            TxtCliPesquisar.setEnabled(false);
            BtnAdicionar.setEnabled(false);
            TblClientes.setVisible(false);

            BtnAtualizar.setEnabled(true);
            BtnRemover.setEnabled(true);
            BtnImprimir.setEnabled(true);
            BtnPesquisar.setEnabled(true);

        } else {
            JOptionPane.showMessageDialog(null, "OS não cadastrada");
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Número da OS inválido");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao pesquisar OS: " + e.getMessage());
    }
}

   
   private void alterar_os() {

    if (TxtCliId.getText().isEmpty() ||
        TxtOsEquipe.getText().isEmpty() ||
        TxtOsDef.getText().isEmpty() ||
        CboOsSit.getSelectedItem().equals(" ")) {

        JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
        return;
    }

    try {
        OrdemServico os = new OrdemServico();

        os.setOs(TxtOs.getText());
        os.setTipo(tipo); // você já controla com o radio button
        os.setSituacao(CboOsSit.getSelectedItem().toString());
        os.setEquipamento(TxtOsEquipe.getText());
        os.setDefeito(TxtOsDef.getText());
        os.setServico(TxtOsServ.getText());
        os.setTecnico(TxtOsTec.getText());
        os.setValor(TxtOsValor.getText().replace(",", "."));
        os.setIdCliente(TxtCliId.getText());

        OrdemServicoDAO dao = new OrdemServicoDAO();
        boolean sucesso = dao.alterar(os);

        if (sucesso) {
            JOptionPane.showMessageDialog(null, "OS alterada com sucesso!");
            limpar();
        } else {
            JOptionPane.showMessageDialog(null, "Nenhuma OS foi alterada.");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao alterar OS: " + e.getMessage());
    }
}

   
  private void excluir_os() {

    int confirma = JOptionPane.showConfirmDialog(
            null,
            "Tem certeza que deseja excluir esta OS?",
            "Atenção",
            JOptionPane.YES_NO_OPTION
    );

    if (confirma == JOptionPane.YES_OPTION) {

        OrdemServicoDAO dao = new OrdemServicoDAO();
        boolean sucesso = dao.excluir(TxtOs.getText());

        if (sucesso) {
            JOptionPane.showMessageDialog(null, "OS excluída com sucesso!");
            limpar();
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao excluir OS.");
        }
    }
}
   
   private void imprimir_os(){
       int confirma = JOptionPane.showConfirmDialog(null, "Confirma impressão desta OS?","Atenção",JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION){
            try {
                
                HashMap filtro = new HashMap();
                filtro.put("os", Integer.parseInt(TxtOs.getText()));
                JasperPrint print = JasperFillManager.fillReport("C:\\Users\\wande\\OneDrive\\Desktop\\reports\\os.jasper",filtro,conexao);
                
                
             JasperViewer.viewReport(print,false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
                                            

    }  
   
   private void recuperarOs(){
       String sql = "select max(os) from tbos";
       
       try {
           pst=conexao.prepareStatement(sql);
           rs=pst.executeQuery();
           
           if(rs.next()){
           TxtOs.setText(rs.getString(1));
       }
       } catch (Exception e) {
       }
   }

   
   
   private void limpar(){
       TxtOs.setText(null);
      
                   TxtData.setText(null);
                   TxtCliId.setText(null);
                   TxtOsEquipe.setText(null);
                   TxtOsDef.setText(null);
                   TxtOsServ.setText(null);
                   TxtOsTec.setText(null);
                   TxtOsValor.setText(null);
                   ((DefaultTableModel) TblClientes.getModel()).setRowCount(0);
                   CboOsSit.setSelectedItem(" ");
                   
                   
                   BtnAtualizar.setEnabled(false);
                   BtnRemover.setEnabled(false);
                   BtnImprimir.setEnabled(false);
                   
                   BtnAdicionar.setEnabled(true);
                   BtnPesquisar.setEnabled(true);
                   TxtCliPesquisar.setEnabled(true);
                   TblClientes.setVisible(true);
                   
                   
                   
   }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TxtOs = new javax.swing.JTextField();
        TxtData = new javax.swing.JTextField();
        RbtOrc = new javax.swing.JRadioButton();
        RbtOs = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        CboOsSit = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        TxtCliPesquisar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        TxtCliId = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblClientes = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        TxtOsEquipe = new javax.swing.JTextField();
        TxtOsDef = new javax.swing.JTextField();
        TxtOsServ = new javax.swing.JTextField();
        TxtOsTec = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        TxtOsValor = new javax.swing.JTextField();
        BtnAdicionar = new javax.swing.JButton();
        BtnPesquisar = new javax.swing.JButton();
        BtnAtualizar = new javax.swing.JButton();
        BtnRemover = new javax.swing.JButton();
        BtnImprimir = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("OS");
        setMaximumSize(new java.awt.Dimension(640, 480));
        setMinimumSize(new java.awt.Dimension(640, 480));
        setPreferredSize(new java.awt.Dimension(640, 480));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("N° OS");

        jLabel2.setText("Data");

        TxtOs.setEditable(false);
        TxtOs.setBackground(new java.awt.Color(204, 204, 204));
        TxtOs.setText(" ");

        TxtData.setEditable(false);
        TxtData.setBackground(new java.awt.Color(204, 204, 204));
        TxtData.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        buttonGroup1.add(RbtOrc);
        RbtOrc.setText("Orçamento");
        RbtOrc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RbtOrcActionPerformed(evt);
            }
        });

        buttonGroup1.add(RbtOs);
        RbtOs.setText("Ordem de Serviço");
        RbtOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RbtOsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addComponent(TxtOs, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RbtOrc))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TxtData)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(RbtOs, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RbtOrc)
                    .addComponent(RbtOs))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 260, -1));

        jLabel3.setText("*Situação");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        CboOsSit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "na bancada", "orçamento reprovado", "orçamento aprovado", "aguardando peças", "abandonado pelo cliente", "entrega ok", "retornou", " " }));
        getContentPane().add(CboOsSit, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, -1, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));
        jPanel2.setMaximumSize(new java.awt.Dimension(333, 157));
        jPanel2.setMinimumSize(new java.awt.Dimension(333, 157));

        TxtCliPesquisar.setText(" ");
        TxtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtCliPesquisarKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br.com.infox.icones/lupa (2).png"))); // NOI18N

        jLabel5.setText("*Id");

        TxtCliId.setEditable(false);
        TxtCliId.setBackground(new java.awt.Color(204, 204, 204));
        TxtCliId.setText("                   ");
        TxtCliId.setMaximumSize(new java.awt.Dimension(72, 22));
        TxtCliId.setMinimumSize(new java.awt.Dimension(72, 22));
        TxtCliId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtCliIdActionPerformed(evt);
            }
        });

        TblClientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        TblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id ", "Nome", "Fone"
            }
        ));
        TblClientes.setFocusable(false);
        TblClientes.setMaximumSize(new java.awt.Dimension(225, 80));
        TblClientes.setMinimumSize(new java.awt.Dimension(225, 80));
        TblClientes.getTableHeader().setReorderingAllowed(false);
        TblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TblClientes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(TxtCliPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TxtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(TxtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(TxtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, -1, 180));

        jLabel6.setText("*Equipamento");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 204, -1, -1));

        jLabel7.setText("*Defeito");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, -1, -1));

        jLabel8.setText("Serviço");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, -1, -1));

        jLabel9.setText("Técnico");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, -1, -1));

        TxtOsEquipe.setText(" ");
        getContentPane().add(TxtOsEquipe, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 201, 516, -1));
        getContentPane().add(TxtOsDef, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, 516, -1));
        getContentPane().add(TxtOsServ, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 280, 516, -1));
        getContentPane().add(TxtOsTec, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 320, 196, -1));

        jLabel10.setText("Valor Total");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 320, -1, -1));

        TxtOsValor.setText("0,00");
        getContentPane().add(TxtOsValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 320, 235, -1));

        BtnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br.com.infox.icones/add.png"))); // NOI18N
        BtnAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAdicionarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 370, -1, -1));

        BtnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br.com.infox.icones/lupac.png"))); // NOI18N
        BtnPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPesquisarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 370, -1, -1));

        BtnAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lapis(1).png"))); // NOI18N
        BtnAtualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnAtualizar.setEnabled(false);
        BtnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAtualizarActionPerformed(evt);
            }
        });
        getContentPane().add(BtnAtualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 370, -1, -1));

        BtnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br.com.infox.icones/deletar.png"))); // NOI18N
        BtnRemover.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnRemover.setEnabled(false);
        BtnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRemoverActionPerformed(evt);
            }
        });
        getContentPane().add(BtnRemover, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 370, -1, -1));

        BtnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br.com.infox.icones/imprimir.png"))); // NOI18N
        BtnImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnImprimir.setEnabled(false);
        BtnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnImprimirActionPerformed(evt);
            }
        });
        getContentPane().add(BtnImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 370, -1, -1));

        setBounds(0, 0, 640, 546);
    }// </editor-fold>//GEN-END:initComponents

    private void TxtCliIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtCliIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtCliIdActionPerformed

    private void TxtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtCliPesquisarKeyReleased
        pesquisar_cliente();
    }//GEN-LAST:event_TxtCliPesquisarKeyReleased

    private void TblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblClientesMouseClicked
        setar_campos();
    }//GEN-LAST:event_TblClientesMouseClicked

    private void RbtOrcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RbtOrcActionPerformed
        tipo = "Orçamento";
    }//GEN-LAST:event_RbtOrcActionPerformed

    private void RbtOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RbtOsActionPerformed
        tipo = "OS";
    }//GEN-LAST:event_RbtOsActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        RbtOrc.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void BtnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAdicionarActionPerformed
        emitir_os();
    }//GEN-LAST:event_BtnAdicionarActionPerformed

    private void BtnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPesquisarActionPerformed
        pesquisar_os();
    }//GEN-LAST:event_BtnPesquisarActionPerformed

    private void BtnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAtualizarActionPerformed
        alterar_os();
    }//GEN-LAST:event_BtnAtualizarActionPerformed

    private void BtnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRemoverActionPerformed
        excluir_os();
    }//GEN-LAST:event_BtnRemoverActionPerformed

    private void BtnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnImprimirActionPerformed
        imprimir_os();
    }//GEN-LAST:event_BtnImprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAdicionar;
    private javax.swing.JButton BtnAtualizar;
    private javax.swing.JButton BtnImprimir;
    private javax.swing.JButton BtnPesquisar;
    private javax.swing.JButton BtnRemover;
    private javax.swing.JComboBox<String> CboOsSit;
    private javax.swing.JRadioButton RbtOrc;
    private javax.swing.JRadioButton RbtOs;
    private javax.swing.JTable TblClientes;
    private javax.swing.JTextField TxtCliId;
    private javax.swing.JTextField TxtCliPesquisar;
    private javax.swing.JTextField TxtData;
    private javax.swing.JTextField TxtOs;
    private javax.swing.JTextField TxtOsDef;
    private javax.swing.JTextField TxtOsEquipe;
    private javax.swing.JTextField TxtOsServ;
    private javax.swing.JTextField TxtOsTec;
    private javax.swing.JTextField TxtOsValor;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
