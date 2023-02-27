package com.nttdata.domain.contract;

import com.nttdata.domain.models.OperationDto;
import io.smallrye.mutiny.Uni;

public interface OperationRepository {
  Uni<OperationDto> addOperation(OperationDto operationDto);
}
