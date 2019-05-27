package org.tron.core.zen.address;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.tron.common.utils.ByteUtil;
import org.tron.common.zksnark.Librustzcash;
import org.tron.common.zksnark.LibrustzcashParam.IvkToPkdParams;
import org.tron.core.exception.ZksnarkException;

// ivk
@Slf4j(topic = "shieldTransaction")
@AllArgsConstructor
public class IncomingViewingKey {

  @Setter
  @Getter
  public byte[] value; // 256

  public Optional<PaymentAddress> address(DiversifierT d) throws ZksnarkException {
    byte[] pkD = new byte[32]; // 32
    if (Librustzcash.librustzcashCheckDiversifier(d.data)) {
      Librustzcash.librustzcashIvkToPkd(new IvkToPkdParams(value, d.data, pkD));
      logger.debug("address.ivk is: " + ByteUtil.toHexString(value));
      logger.debug("address.d is: " + ByteUtil.toHexString(d.data));
      logger.debug("address.pkd is: " + ByteUtil.toHexString(pkD));
      return Optional.of(new PaymentAddress(d, pkD));
    } else {
      return Optional.empty();
    }
  }
}
