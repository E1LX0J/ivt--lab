package hu.bme.mit.spaceship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//Just to cause some problems :P
public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primaryTorpedoMock;
  private TorpedoStore secondaryTorpedoMock;


  @BeforeEach
  public void init(){
    this.primaryTorpedoMock = mock(TorpedoStore.class);
    this.secondaryTorpedoMock = mock(TorpedoStore.class);
    this.ship = new GT4500(primaryTorpedoMock, secondaryTorpedoMock);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primaryTorpedoMock.fire(1)).thenReturn(true);
    when(primaryTorpedoMock.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertTrue(result);
    // Assert
    verify(primaryTorpedoMock, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){

    // Arrange
    when(primaryTorpedoMock.isEmpty()).thenReturn(false);
    when(primaryTorpedoMock.fire(1)).thenReturn(true);
    when(secondaryTorpedoMock.isEmpty()).thenReturn(false);
    when(secondaryTorpedoMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertTrue(result);
    verify(primaryTorpedoMock, times(1)).fire(1);
    verify(secondaryTorpedoMock, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Fail_Due_To_Empty(){
    when(primaryTorpedoMock.isEmpty()).thenReturn(false);
    when(secondaryTorpedoMock.isEmpty()).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    assertFalse(result);

    verify(primaryTorpedoMock, times(1)).isEmpty();
    verify(secondaryTorpedoMock, times(1)).isEmpty();
  }

  @Test
  public void fireTorpedo_Single_Twice(){
    when(primaryTorpedoMock.isEmpty()).thenReturn(false);
    when(primaryTorpedoMock.fire(1)).thenReturn(true);
    when(secondaryTorpedoMock.isEmpty()).thenReturn(false);
    when(secondaryTorpedoMock.fire(1)).thenReturn(true);


    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    assertTrue(result1 && result2);

    verify(primaryTorpedoMock, times(1)).fire(1);
    verify(secondaryTorpedoMock, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Twice_Secondary_Empty(){
    when(primaryTorpedoMock.isEmpty()).thenReturn(false);
    when(primaryTorpedoMock.fire(1)).thenReturn(true);
    when(secondaryTorpedoMock.isEmpty()).thenReturn(true);

    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    assertTrue(result1 && result2);

    verify(primaryTorpedoMock, times(2)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Twice_Primary_Empty(){
    when(secondaryTorpedoMock.isEmpty()).thenReturn(false);
    when(secondaryTorpedoMock.fire(1)).thenReturn(true);
    when(primaryTorpedoMock.isEmpty()).thenReturn(true);

    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    assertTrue(result1 && result2);

    verify(secondaryTorpedoMock, times(2)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Primary_Fail(){
    when(primaryTorpedoMock.isEmpty()).thenReturn(false);
    when(primaryTorpedoMock.fire(1)).thenReturn(false);
    when(secondaryTorpedoMock.isEmpty()).thenReturn(false);
    when(secondaryTorpedoMock.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertFalse(result);

    verify(secondaryTorpedoMock, never()).fire(1);
    verify(primaryTorpedoMock, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Secondary_Fail(){
    when(primaryTorpedoMock.isEmpty()).thenReturn(true);
    when(secondaryTorpedoMock.isEmpty()).thenReturn(false);
    when(secondaryTorpedoMock.fire(1)).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertFalse(result);

    verify(secondaryTorpedoMock, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Twice_Primary_Then_Secondary_Empty(){
    when(primaryTorpedoMock.isEmpty()).thenReturn(false);
    when(primaryTorpedoMock.fire(1)).thenReturn(true);

    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);

    when(primaryTorpedoMock.isEmpty()).thenReturn(true);
    when(secondaryTorpedoMock.isEmpty()).thenReturn(true);

    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    assertFalse(result2);
  }

  @Test
  public void fireTorpedo_Single_Primary_Empty_Secondary_Empty(){
    when(primaryTorpedoMock.isEmpty()).thenReturn(true);
    when(secondaryTorpedoMock.isEmpty()).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertFalse(result);
  }

  @Test
  public void fireTorpedo_All_Only_Primary(){
    when(primaryTorpedoMock.isEmpty()).thenReturn(false);
    when(secondaryTorpedoMock.isEmpty()).thenReturn(true);
    when(primaryTorpedoMock.fire(1)).thenReturn(true);


    boolean result = ship.fireTorpedo(FiringMode.ALL);

    assertTrue(result);
  }

  @Test
  public void fireTorpedo_All_Only_Secondary(){
    when(secondaryTorpedoMock.isEmpty()).thenReturn(false);
    when(primaryTorpedoMock.isEmpty()).thenReturn(true);
    when(secondaryTorpedoMock.fire(1)).thenReturn(true);


    boolean result = ship.fireTorpedo(FiringMode.ALL);
    assertTrue(result);
  }

  @Test
  public void fireTorpedo_Default(){
    boolean result = ship.fireTorpedo(FiringMode.WOW);
    assertFalse(result);
  }
}
