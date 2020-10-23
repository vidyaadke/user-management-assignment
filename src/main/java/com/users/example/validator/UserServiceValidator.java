package com.users.example.validator;

import com.users.example.enums.ApiErrorCode;
import com.users.example.enums.Gender;
import com.users.example.enums.State;
import com.users.example.enums.Title;
import com.users.example.gen.model.ErrorDetail;
import com.users.example.gen.model.UpdateUserRequest;
import com.users.example.util.ErrorResponseBuilder;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component public class UserServiceValidator {

    ErrorResponseBuilder errorResponseBuilder = new ErrorResponseBuilder();

    /**
     * Function to validate get user request
     */
    public List<ErrorDetail> validateRequest(Integer userId) {
        List<ErrorDetail> errorDetails = new ArrayList<>();

        if (userId == null) {
            errorDetails.add(errorResponseBuilder.buildErrorDetail(ApiErrorCode.PARAMETER_NULL.name(), "Path Parameter 'userId' is null.", "userId"));
        }

        return errorDetails;
    }

    /**
     * Function to validate update user request
     */
    public List<ErrorDetail> validateRequest(Integer userId, UpdateUserRequest request) {
        List<ErrorDetail> errorDetails = new ArrayList<>();

        if (userId == null) {
            errorDetails.add(errorResponseBuilder.buildErrorDetail(ApiErrorCode.PARAMETER_NULL.name(), "Path Parameter 'userId' is null.", "userId"));
        }

        if (request == null) {
            errorDetails.add(errorResponseBuilder.buildErrorDetail(ApiErrorCode.PARAMETER_NULL.name(), "Request is null or empty.", "request"));

        }

        //validate gender if exist
        if (request != null && request.getGender() != null) {
            if (!EnumUtils.isValidEnum(Gender.class, request.getGender())) {
                errorDetails.add(errorResponseBuilder.buildErrorDetail(ApiErrorCode.PARAMETER_INVALID_VALUE.name(),
                    "Gender has invalid value, correct value is one of the : " + Stream.of(Gender.values()).map(Gender::getDescription)
                        .collect(Collectors.joining(", ")), "gender"));

            }
        }

        //validate title if exist
        if (request != null && request.getTitle() != null) {
            if (!EnumUtils.isValidEnum(Title.class, request.getTitle())) {
                errorDetails.add(errorResponseBuilder.buildErrorDetail(ApiErrorCode.PARAMETER_INVALID_VALUE.name(),
                    "Title has invalid value, correct value is one of the : " + Stream.of(Title.values()).map(Title::getDescription)
                        .collect(Collectors.joining(", ")), "title"));

            }
        }

        //validate address state if exist
        if (request != null && request.getAddress() != null) {
            if (!EnumUtils.isValidEnum(State.class, request.getAddress().getState())) {
                errorDetails.add(errorResponseBuilder.buildErrorDetail(ApiErrorCode.PARAMETER_INVALID_VALUE.name(),
                    "state has invalid value, correct value is one of the : " + Stream.of(State.values()).map(State::getDescription)
                        .collect(Collectors.joining(", ")), "address.state"));

            }
        }

        return errorDetails;
    }

}
