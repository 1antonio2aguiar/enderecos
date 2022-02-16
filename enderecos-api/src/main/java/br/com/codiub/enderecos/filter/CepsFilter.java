package br.com.codiub.enderecos.filter;

import java.util.Date;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class CepsFilter {
  @Valid private BairrosFilter bairrosFilter;

  @Size(max = 10)
  private String cep;

  @Size(max = 10)
  private Long codigoCorreios;

  @Size(max = 11)
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Date dataAlteracao;

  @NotNull
  @Size(min = 0, max = 10)
  private Long id;

  @Size(max = 1)
  private String identificacao;
  
  //@Valid private TiposLogradourosFilter TiposLogradourosFilter;

  @Valid private LogradourosFilter logradourosFilter;

  @Size(max = 10)
  private Long numeroFim;

  @Size(max = 10)
  private Long numeroIni;

  @Size(max = 50)
  private String usuario;
}
