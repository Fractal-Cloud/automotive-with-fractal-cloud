package cloud.fractal.samples.automotive.environment;

import com.yanchware.fractal.sdk.Automaton;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentAggregate;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentIdValue;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemAggregate;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemIdValue;
import com.yanchware.fractal.sdk.domain.livesystem.operationalservicewindow.OperationalServiceWindow;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.EnumSet;
import java.util.List;

public class AutomotiveEnvironment {
  private final Automaton automaton;
  private final EnvironmentAggregate environmentAggregate;
  private final EnvironmentIdValue environmentId;

  public AutomotiveEnvironment(Automaton automaton, EnvironmentAggregate environmentAggregate, EnvironmentIdValue environmentId) {
    this.automaton = automaton;
    this.environmentAggregate = environmentAggregate;
    this.environmentId = environmentId;
  }

  public void instantiate() throws InstantiatorException {
    automaton.instantiate(environmentAggregate);
  }

  public void deploy(List<AutomotiveSystem> automotiveSystems) throws InstantiatorException {
//    var instantiationConfig =
//      InstantiationConfiguration.builder().withWaitConfiguration(InstantiationWaitConfiguration.builder()
//      .withWaitForInstantiation(true)
//      .withTimeoutMinutes(30)
//      .build()).build();

    automaton.instantiate(automotiveSystems.stream()
      .map(this::getLiveSystem)
      .toList()/*, instantiationConfig*/);
  }

  public void delete(List<LiveSystemIdValue> fractalBankSystemIds) throws InstantiatorException {
    automaton.delete(environmentAggregate.getManagementEnvironment().getId(), fractalBankSystemIds);
  }

  protected LiveSystemAggregate getLiveSystem(AutomotiveSystem AutomotiveSystem) {
    return automaton.getLiveSystemBuilder()
      .withId(AutomotiveSystem.liveSystemId())
      .withFractalId(AutomotiveSystem.fractalId())
      .withDescription(AutomotiveSystem.description())
      .withComponents(AutomotiveSystem.components())
      .withEnvironmentId(environmentId)
      .withStandardProvider(ProviderType.AZURE)
      .withServiceWindow(OperationalServiceWindow.builder("ChristmasAndSaturdayNight", ZoneId.of("Europe/Copenhagen"))
        .withOneOffRule(LocalDate.of(2025, 12, 25), LocalTime.MIDNIGHT, LocalTime.NOON)
        .withWeeklyRule(EnumSet.of(DayOfWeek.SATURDAY), LocalTime.of(20, 0), LocalTime.of(6, 0))
        .build())
      .build();
  }
}
