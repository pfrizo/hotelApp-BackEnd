package poo.aps.hotelApp;

public class Response {
    public String status;
    public String message;
    public Object object;

    public Response(Object obj){
        this.status = "OK";
        this.object = obj;
    }

    public Response(String error){
        this.status = "Error";
        this.message = error;
    }
}
