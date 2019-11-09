package network;

import app.DTO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable
{
    private Socket socket;
    private DTO dto;

    ClientHandler(Socket socket)
    {
        dto = DTO.getInstance();
        dto.getClients().add(socket);
        this.socket = socket;
    }

    public Socket getSocket()
    {
        return socket;
    }

    @Override
    public void run()
    {
        try
        {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String[] request;
            String response;

            System.out.println("Client " + socket.getInetAddress().getHostAddress() + " connected");

            while (true)
            {
                request = in.readUTF().split("/");
                System.out.println("Received request from " + socket.getInetAddress().getHostAddress() + ": " + request[0]);

                switch (request[0])
                {
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
//                        User user = JSONParser.objectFromJson(request[1], User.class);
//                        ResultSet rs = stageController.dbHandler.select(new String[]{"*"}, new String[]{"users"}, new String[]{"login"}, new String[]{"="}, new Object[]{user.getLogin()}, null);
//
//                        response = "success";
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
//                        User user = JSONParser.objectFromJson(request[1], User.class);
//                        ResultSet rs = stageController.dbHandler.select(new String[]{"*"}, new String[]{"users"}, new String[]{"login", "password"}, new String[]{"=", "="}, new Object[]{user.getLogin(), user.getPassword()}, new String[]{"AND"});
//                        response = "failed"
//                        try
//                        {
//                            while (rs.next())
//                            {
//                                response = rs.getString("post");
//                                flag++;
//                            }
//                        }
//                        catch (Exception ex)
//                        {
//                            System.out.println(ex);
//                        }
//
//
//                        out.writeUTF(response);
//                        out.flush();
//
//                        break;
//                    }
//
//                    case "insertUser":
//                    {
//                        User user = JSONParser.objectFromJson(request[1], User.class);
//
//                        stageController.dbHandler.insert(new String[]{"users"}, new String[]{"idusers", "login", "password", "fio", "post"}, new Object[]{user.getId(), user.getLogin(), user.getPassword(), user.getFio(), user.getPost()});
//
//                        break;
//                    }
//
//                    case "updateUsersTable":
//                    {
//                        User[] users = JSONParser.objectFromJson(request[1], User[].class);
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
//                        Account[] accounts = JSONParser.objectFromJson(request[1], Account[].class);
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


                    case "disconnect":
                    {
                        System.out.println("Client " + socket.getInetAddress().getHostAddress() + " disconnected");
                        dto.getClients().remove(socket);
                        socket.close();

                        return;
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}