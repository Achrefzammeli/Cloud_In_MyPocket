package tn.esprit.cloud_in_mypocket.dto;


public class ContractDTO {
    private String content;
    private String deadline;
    private String status;
    private Long lawyerId;
    private Long clientId;
    private String pdfBase64;

    public String getContent() { return content; }
    public String getDeadline() { return deadline; }
    public String getStatus() { return status; }
    public Long getLawyerId() { return lawyerId; }
    public Long getClientId() { return clientId; }


    @Override
    public String toString() {
        return "ContractDTO{" +
                "content='" + content + '\'' +
                ", deadline='" + deadline + '\'' +
                ", status='" + status + '\'' +
                ", lawyerId=" + lawyerId +
                ", clientId=" + clientId +
                '}';
    }


}

