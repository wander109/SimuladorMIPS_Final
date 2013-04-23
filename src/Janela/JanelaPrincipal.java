package Janela;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import ManipulacaoArquivo.TrataArquivo;
import Memoria.Memoria;
import Memoria.Registro;
import Processador.Montador;
import Processador.UCP;

/**
 *
 * @author Jefferson
 */
public class JanelaPrincipal extends javax.swing.JFrame {
	
	// Variables declaration - do not modify                     
    //===========Obejetos da Tela======================================
	private javax.swing.JButton btAbrir;
    private javax.swing.JButton btRodar;
    private javax.swing.JButton btStep;
    private javax.swing.JLabel lbCaminho;
    private javax.swing.JScrollPane spViewTabelaRegistradores;
    private javax.swing.JScrollPane spViewTabelaMemoria;
    private javax.swing.JTable tbRegistradores;
    private javax.swing.JTable tbMemoria;
    private javax.swing.JTextField tfCaminho;
    //===========Objetos para carregar e representar==================
    //===============os registradores em tela=========================
    private TrataArquivo abreCodigoFonte;
    private TrataArquivo carregaRegistradores;
    private TreeMap<String, String> listaDeRegistradores;
    private TreeMap<String, Integer> listaDeRegistradoresValor;
    //==========Objetos do processador para realizar==================
    //========a traducao e interpretacao do codigo MIPS===============
    int pc = 0;
    private Memoria memoria;
    private Montador montador;
    private UCP ucp = new UCP("Traducoes/InstrucoesRFormatBIN.txt","Traducoes/InstrucoesBIN.txt");
    // End of variables declaration
    
    /**
     * Creates new form SimuladorMIPS
     */
    public JanelaPrincipal() {
    	super("Simulador MIPS");
        initComponents();
    }

    //Metodo que instancia e inicializa todos os componentes com suas propriedades necessarias
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        spViewTabelaRegistradores = new javax.swing.JScrollPane();
        tbRegistradores = new javax.swing.JTable();
        spViewTabelaMemoria = new javax.swing.JScrollPane();
        tbMemoria = new javax.swing.JTable();
        lbCaminho = new javax.swing.JLabel();
        tfCaminho = new javax.swing.JTextField();
        btAbrir = new javax.swing.JButton();
        btRodar = new javax.swing.JButton();
        btStep = new javax.swing.JButton();
        abreCodigoFonte = new TrataArquivo();
        carregaRegistradores = new TrataArquivo("Traducoes/Registradores.txt");
        listaDeRegistradoresValor = new TreeMap<String, Integer>();
        memoria = new Memoria();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(960, 800));
        
        carregaListaDeRegistradores();
        tbRegistradores.setModel(new javax.swing.table.DefaultTableModel(
            getTabelaDeRegistradores(),
            new String [] {
                "Registradores", "Número", "Valor"
            }
        ));
        tbRegistradores.setAutoCreateRowSorter(true);
        tbRegistradores.getRowSorter().toggleSortOrder(1);
        tbRegistradores.setEnabled(false);
        spViewTabelaRegistradores.setViewportView(tbRegistradores);

        tbMemoria.setModel(new javax.swing.table.DefaultTableModel(
            null,
            new String [] {
                "Linha", "Código Fonte", "Memória"
            }
        ));
        
        tbMemoria.getColumn("Linha").setMaxWidth(70);
        tbMemoria.setEnabled(false);
        
        spViewTabelaMemoria.setViewportView(tbMemoria);

        lbCaminho.setText("Caminho:");

        tfCaminho.setEditable(false);

        btAbrir.setText("Abrir arquivo");
        btAbrir.addMouseListener(clickBtAbrir);
        
        btRodar.setText("Rodar");
        btRodar.addMouseListener(clickBtRodar);

        btStep.setText("Step");
        btStep.addMouseListener(clickBtStep);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbCaminho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfCaminho, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAbrir))
                    .addComponent(spViewTabelaMemoria, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(spViewTabelaRegistradores, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(197, 197, 197)
                .addComponent(btRodar)
                .addGap(38, 38, 38)
                .addComponent(btStep)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spViewTabelaMemoria, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addComponent(spViewTabelaRegistradores))
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCaminho)
                    .addComponent(tfCaminho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btAbrir))
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btRodar)
                    .addComponent(btStep))
                .addGap(75, 75, 75))
        );

        pack();
    }// </editor-fold>                        
    
    private MouseAdapter clickBtAbrir = new MouseAdapter() {
    	TrataArquivo leitorDeTraducoes;
    	ArrayList<String> codigoFonte = new ArrayList<String>();
		TreeMap<String, String> dicionarioInstrucoes = new TreeMap<String, String>();
		TreeMap<String, String> dicionarioRegistradores = new TreeMap<String, String>();
		TreeMap<String, String> dicionarioInstrucoesTipo = new TreeMap<String, String>();
		TreeMap<String, String> dicionarioInstrucoesRFormat = new TreeMap<String, String>();
		TreeMap<String, Integer> dicionarioLabels = new TreeMap<String, Integer>();
		
    	@Override
    	public void mouseClicked(MouseEvent e) {
    		try {
				abreCodigoFonte.selecionarArquivo();
				
				codigoFonte = abreCodigoFonte.getArquivoSalvo();
				
				//Manipulador de arquivo para ler o "dicionario" da traducao das instrucoes
				leitorDeTraducoes = new TrataArquivo("Traducoes/Instrucoes.txt");
				dicionarioInstrucoes = leitorDeTraducoes.carregaMapaDeTraducao();
				
				leitorDeTraducoes = new TrataArquivo("Traducoes/Registradores.txt");
				dicionarioRegistradores = leitorDeTraducoes.carregaMapaDeTraducao();
				
				
				leitorDeTraducoes = new TrataArquivo("Traducoes/InstrucoesTipo.txt");
				dicionarioInstrucoesTipo = leitorDeTraducoes.carregaMapaDeTraducao();
				
				leitorDeTraducoes = new TrataArquivo("Traducoes/InstrucoesRFormat.txt");
				dicionarioInstrucoesRFormat = leitorDeTraducoes.carregaMapaDeTraducao();
				
				dicionarioLabels = leitorDeTraducoes.getDicionarioLabels(codigoFonte);
				
				montador = new Montador(codigoFonte,
						  				dicionarioRegistradores,
						  				dicionarioInstrucoes,
						  				dicionarioInstrucoesRFormat,
						  				dicionarioInstrucoesTipo,
						  				dicionarioLabels);

				memoria.setCodigoFonteBinario(montador.traduzir());
				
				tbMemoria.setModel(new javax.swing.table.DefaultTableModel(
			            getTabelaLinhaCodigoFonte(),
			            new String [] {
			                "Linha", "Código Fonte", "Memória"
			            }
			    ));
				tbMemoria.getColumn("Linha").setMaxWidth(70);
				tbMemoria.setRowSelectionInterval(0, 0);
		       
			} catch (IOException e1) {
				e1.printStackTrace();
			}
    		tfCaminho.setText(abreCodigoFonte.getCaminhoArquivo());
    	}
	};
	
	private MouseAdapter clickBtStep = new MouseAdapter() {
		
		@Override
    	public void mouseClicked(MouseEvent e) {
			
			memoria = new Memoria();
			ArrayList prog = new ArrayList();
			memoria.setCodigoFonteBinario(montador.traduzir());
			prog = memoria.getCodigoFonteBinario();
			
			if(pc < tbMemoria.getRowCount())
				tbMemoria.setRowSelectionInterval(pc, pc);
			try {
				System.out.println("ler instrução");
			
				
				
					pc = ucp.lerInterpretarInstrucao(prog.get(pc).toString(), pc);
					
					tbRegistradores.setModel(new javax.swing.table.DefaultTableModel(
				            getTabelaDeRegistradores(),
				            new String [] {
				                "Registradores", "Número", "Valor"
				                
				            }
				        ));
				
				
					
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		}
	};
	
	private MouseAdapter clickBtRodar = new MouseAdapter() {
		
		@Override
    	public void mouseClicked(MouseEvent e) {
			System.out.println("rodou");
			try {
				System.out.println("ler instrução");
			
				memoria = new Memoria();
				ArrayList prog = new ArrayList();
				memoria.setCodigoFonteBinario(montador.traduzir());
				prog = memoria.getCodigoFonteBinario();
				while (pc != prog.size()){
					pc = ucp.lerInterpretarInstrucao(prog.get(pc).toString(), pc);
					
					tbRegistradores.setModel(new javax.swing.table.DefaultTableModel(
				            getTabelaDeRegistradores(),
				            new String [] {
				                "Registradores", "Número", "Valor"
				                
				            }
				        ));
				}
				
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	};
	
	private void carregaListaDeRegistradores(){
		try {
			listaDeRegistradores = carregaRegistradores.carregaMapaDeTraducao();
			
			for(int i = 0; i < listaDeRegistradores.size(); i++)
				//olha que doido isso, talvez funfe
				listaDeRegistradoresValor.put((String)listaDeRegistradores.keySet().toArray()[i], (int)ucp.converteParaDecimal(ucp.registradores.getValorRegistrador((String)listaDeRegistradores.keySet().toArray()[i])));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Object[][] getTabelaDeRegistradores(){
		Object[] registradores = listaDeRegistradoresValor.keySet().toArray();
		Object[][] matrizRegistradorNumeroValor = new Object[registradores.length][3];
		ArrayList<Registro> regs = new ArrayList<Registro>();
		
		for(int i = 0; i < listaDeRegistradoresValor.size(); i++){
			matrizRegistradorNumeroValor[i][0] = (String)registradores[i];
			matrizRegistradorNumeroValor[i][1] = listaDeRegistradores.get((String)registradores[i]);
			matrizRegistradorNumeroValor[i][2] = (int)ucp.converteParaDecimal(ucp.registradores.getValorRegistrador((String)registradores[i]));
		}
		
		return matrizRegistradorNumeroValor;
	}
	
	private Object[][] getTabelaLinhaCodigoFonte(){
		Object[] codigoFonte = montador.getCodigoFonte().toArray();
		Object[] codigoFonteBinario = memoria.getCodigoFonteBinario().toArray();
		Object[][] matrizCodigoFonte = new Object[codigoFonte.length-1][3];
		
		for(int i = 0; i < codigoFonte.length-1; i++){
			matrizCodigoFonte[i][0] = i;
			matrizCodigoFonte[i][1] = (String)codigoFonte[i];
			matrizCodigoFonte[i][2] = (String)codigoFonteBinario[i];
		}
		
		return matrizCodigoFonte;
	}
	
	/**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JanelaPrincipal().setVisible(true);
            }
        });
    }
                       
}

