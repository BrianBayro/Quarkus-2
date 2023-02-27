package com.nttdata.btask.service;

import com.nttdata.btask.interfaces.CreditService;
import com.nttdata.domain.contract.CreditRepository;
import com.nttdata.domain.models.CreditDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreditServiceImpl implements CreditService {
  private final CreditRepository creditRepository;

  public CreditServiceImpl(CreditRepository creditRepository) {
    this.creditRepository = creditRepository;
  }

  @Override
  public Multi<CreditDto> list() {
    return creditRepository.list();
  }

  @Override
  public Uni<CreditDto> findByNroAccount(CreditDto creditDto) {
    return creditRepository.findByNroAccount(creditDto);
  }

  @Override
  public Uni<CreditDto> addCredit(CreditDto creditDto) {
    return creditRepository.addCredit(creditDto);
  }

  @Override
  public Uni<CreditDto> updateCredit(CreditDto creditDto) {
    return creditRepository.updateCredit(creditDto);
  }

  @Override
  public Uni<CreditDto> deleteCredit(CreditDto creditDto) {
    return creditRepository.deleteCredit(creditDto);
  }
}
