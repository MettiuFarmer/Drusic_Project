class DBConnection{
  
  private Connection conn;
  private Statement stmt;
  
  public DBConnection(){
    String host = "jdbc:mysql://80.22.95.8:3306/5afattoredb";
    String uName = "5afattore";
    String uPass = "fazzingher";
    
    try{
      this.conn = DriverManager.getConnection(host, uName, uPass);
      this.stmt = this.conn.createStatement();
    }catch(Exception ex){
      
    }
    
  }
  
  public Connection getConnection(){
    return this.conn;
  }
  
  void insertPreset(int idPreset, byte[] preset) throws Exception{
    this.stmt.execute("INSERT INTO progetto");
  }
}