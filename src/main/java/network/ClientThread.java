//package network;
//
//import sample.StageController;
//import sample.classes.Account;
//import sample.classes.MetersData;
//import sample.classes.Tariff;
//import sample.classes.User;
//import sample.database.DBHandler;
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.net.Socket;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.Calendar;
//
//public class ClientThread extends Thread
//{
//    private Socket socket;
//    private StageController stageController;
//
//    ClientThread(Socket socket)
//    {
//        stageController = StageController.getInstance();
//        this.socket = socket;
//    }
//
//    @Override
//    public void run()
//    {
//        try
//        {
//            DataInputStream in = new DataInputStream(socket.getInputStream());
//            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//            stageController.dbHandler.getDBConnection();
//            String query[];
//            String response;
//
//            System.out.println("Client connected");
//            System.out.println("IP: "+ socket.getInetAddress());
//
//            while (!socket.isClosed())
//            {
//                System.out.println("Waiting for query");
//                query = in.readUTF().split("/");
//                System.out.println("Query received: " + query[0]);
//
//                switch (query[0])
//                {
//                    case "sendUsersTable":
//                    {
//                        ArrayList<User> users = new ArrayList<User>();
//                        ResultSet rs = stageController.dbHandler.select(new String[]{"*"}, new String[]{"users"}, null, null, null, null);
//
//                        try
//                        {
//                            while (rs.next())
//                            {
//                                users.add(new User(rs.getInt("idusers"), rs.getString("login"), rs.getString("password"),
//                                        rs.getString("fio"), rs.getString("post")));
//                            }
//                        }
//                        catch (Exception ex)
//                        {
//                            System.out.println(ex);
//                        }
//
//                        response = JSONParser.jsonFromObject(users.toArray(new User[users.size()]));
//
//                        users.clear();
//
//                        out.writeUTF(response);
//                        out.flush();
//
//                        break;
//                    }
//
//                    case "signUp":
//                    {
//                        User user = JSONParser.objectFromJson(query[1], User.class);
//                        ResultSet rs = stageController.dbHandler.select(new String[]{"*"}, new String[]{"users"}, new String[]{"login"}, new String[]{"="}, new Object[]{user.getLogin()}, null);
//
//                        response = "true";
//
//                        try
//                        {
//                            while (rs.next())
//                            {
//                                response = rs.getString("login");
//                            }
//                        }
//                        catch (Exception ex)
//                        {
//                            System.out.println(ex);
//                        }
//
//                        out.writeUTF(response);
//                        out.flush();
//
//                        break;
//                    }
//
//                    case "signIn":
//                    {
//                        User user = JSONParser.objectFromJson(query[1], User.class);
//                        ResultSet rs = stageController.dbHandler.select(new String[]{"*"}, new String[]{"users"}, new String[]{"login", "password"}, new String[]{"=", "="}, new Object[]{user.getLogin(), user.getPassword()}, new String[]{"AND"});
//                        int flag = 0;
//                        String post = "";
//                        try
//                        {
//                            while (rs.next())
//                            {
//                                post = rs.getString("post");
//                                flag++;
//                            }
//                        }
//                        catch (Exception ex)
//                        {
//                            System.out.println(ex);
//                        }
//
//                        if(flag > 0)
//                        {
//                            response = post;
//                        }
//                        else
//                        {
//                            response = "false";
//                        }
//
//                        out.writeUTF(response);
//                        out.flush();
//
//                        break;
//                    }
//
//                    case "insertUser":
//                    {
//                        User user = JSONParser.objectFromJson(query[1], User.class);
//
//                        stageController.dbHandler.insert(new String[]{"users"}, new String[]{"idusers", "login", "password", "fio", "post"}, new Object[]{user.getId(), user.getLogin(), user.getPassword(), user.getFio(), user.getPost()});
//
//                        break;
//                    }
//
//                    case "updateUsersTable":
//                    {
//                        User[] users = JSONParser.objectFromJson(query[1], User[].class);
//
//                        stageController.dbHandler.delete(new String[]{"users"}, new String[]{"*"}, null, null, null);
//
//                        for(int i = 0; i<users.length; i++)
//                        {
//                            stageController.dbHandler.insert(new String[]{"users"}, new String[]{"idusers", "login", "password", "fio", "post"}, new Object[]{users[i].getId(), users[i].getLogin(), users[i].getPassword(), users[i].getFio(), users[i].getPost()});
//                        }
//
//                        break;
//                    }
//
//                    case "sendAccountsTable":
//                    {
//                        ArrayList<Account> accounts = new ArrayList<Account>();
//
//                        ResultSet rs = null;
//                        PreparedStatement ps = null;
//
//                        try
//                        {
//                            ps = DBHandler.getInstance().getDBConnection().prepareStatement("SELECT * FROM accounts INNER JOIN metersdata ON accounts.id=metersdata.accountId");
//                            rs = ps.executeQuery();
//
//                            try
//                            {
//                                while (rs.next())
//                                {
//                                    Calendar tempCalendar = null;
//                                    ;
//
//                                    accounts.add(new Account(rs.getInt("id"), rs.getString("address"), rs.getString("responsible"),
//                                            new MetersData(rs.getDouble("coldWater"), rs.getDouble("hotWater"), rs.getDouble("heating"), rs.getDouble("electricity") ,rs.getDouble("gas")), rs.getDouble("debt"),  rs.getDate("fillingDate")));
//                                }
//                            }
//                            catch (Exception ex)
//                            {
//                                System.out.println(ex);
//                            }
//
//                            response = JSONParser.jsonFromObject(accounts.toArray(new Account[accounts.size()]));
//
//                            accounts.clear();
//
//                            out.writeUTF(response);
//                            out.flush();
//                        }
//                        catch (Exception ex)
//                        {
//                            System.out.println(ex);
//                        }
//
//
//
//                        break;
//                    }
//
//                    case "updateAccountsTable":
//                    {
//                        Account[] accounts = JSONParser.objectFromJson(query[1], Account[].class);
//
//                        stageController.dbHandler.delete(new String[]{"accounts"}, new String[]{"*"}, null, null, null);
//
//                        for(int i = 0; i<accounts.length; i++)
//                        {
//                            stageController.dbHandler.insert(new String[]{"accounts"}, new String[]{"id", "address", "responsible", "debt", "fillingDate"}, new Object[]{accounts[i].getId(), accounts[i].getAddress(),accounts[i].getResponsible(), accounts[i].getDebt(), accounts[i].getFillingDate()});
//                            stageController.dbHandler.insert(new String[]{"metersdata"}, new String[]{"accountId", "coldWater", "hotWater", "heating", "electricity", "gas"}, new Object[]{accounts[i].getId(), accounts[i].getMetersData().getColdWater(),accounts[i].getMetersData().getHotWater(), accounts[i].getMetersData().getHeating(), accounts[i].getMetersData().getElectricity(), accounts[i].getMetersData().getGas()});
//                        }
//
//                        break;
//                    }
//
//                    case "calculateCost":
//                    {
//                        MetersData metersData = JSONParser.objectFromJson(query[1], MetersData.class);
//                        ResultSet rs;
//                        PreparedStatement ps = stageController.dbHandler.getDBConnection().prepareStatement("SELECT * FROM tariff");
//
//                        rs = ps.executeQuery();
//
//                        try
//                        {
//                            rs.next();
//                            Tariff tariff = new Tariff(rs.getDouble("coldWaterCost"), rs.getDouble("hotWaterCost"), rs.getDouble("heatingCost"), rs.getDouble("electricityCost"), rs.getDouble("gasCost"));
//                            response = JSONParser.jsonFromObject(tariff.calculateCost(metersData));
//
//                            out.writeUTF(response);
//                            out.flush();
//
//                        }
//                        catch (Exception ex)
//                        {
//                            System.out.println(ex);
//                        }
//
//                        break;
//                    }
//
//                    case "sendTariff":
//                    {
//                        ResultSet rs;
//                        PreparedStatement ps = stageController.dbHandler.getDBConnection().prepareStatement("SELECT * FROM tariff");
//                        rs = ps.executeQuery();
//
//                        try
//                        {
//                            rs.next();
//                            response = JSONParser.jsonFromObject(new Tariff(rs.getDouble("coldWaterCost"), rs.getDouble("hotWaterCost"), rs.getDouble("heatingCost"), rs.getDouble("electricityCost"), rs.getDouble("gasCost")));
//
//                            out.writeUTF(response);
//                            out.flush();
//
//                        }
//                        catch (Exception ex)
//                        {
//                            System.out.println(ex);
//                        }
//
//                        break;
//                    }
//
//                    case "updateTariff":
//                    {
//                        Tariff tariff = JSONParser.objectFromJson(query[1], Tariff.class);
//
//                        PreparedStatement ps = stageController.dbHandler.getDBConnection().prepareStatement("DELETE FROM tariff");
//                        ps.executeUpdate();
//                        ps = stageController.dbHandler.getDBConnection().prepareStatement("INSERT INTO tariff(coldWaterCost, hotWaterCost, heatingCost, electricityCost, gasCost) VALUES(?,?,?,?,?)");
//                        ps.setDouble(1,tariff.getColdWaterCost());
//                        ps.setDouble(2,tariff.getHotWaterCost());
//                        ps.setDouble(3,tariff.getHeatingCost());
//                        ps.setDouble(4,tariff.getElectricityCost());
//                        ps.setDouble(5,tariff.getGasCost());
//
//                        ps.executeUpdate();
//
//                        break;
//                    }
//
//                    case "exit":
//                    {
//                        System.out.println("Client disconnected");
//                        System.out.println("IP:"+ socket.getInetAddress());
//                        stageController.numberOfConnections--;
//                        socket.close();
//                        Thread.interrupted();
//                    }
//                }
//
//            }
//        }
//        catch (Exception ex)
//        {
//            System.out.println(ex);
//        }
//    }
//}