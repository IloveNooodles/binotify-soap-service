package service;

import enums.ApiResp;
import model.LoggingModel;
import repository.LoggingRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoggingService {
    private final LoggingRepository loggingRepository = new LoggingRepository();

    public List<LoggingModel> getAllLoging(){
        try {
            List<LoggingModel> llm = loggingRepository.getAllLogging();
            return llm;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public String addLoging(LoggingModel lm){
        try {
            loggingRepository.addLogging(lm);
            return ApiResp.LOGGING_SUCCESFUL;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return ApiResp.INTERNAL_SERVER_ERROR;
    }
}
