package hedspi.group01.force.controller;


import hedspi.group01.force.model.Simulation;
import hedspi.group01.force.model.vector.AppliedForce;
import hedspi.group01.force.model.vector.FrictionForce;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class ForcePanelController {
    private static final String NUMBER_REGEX = "^([+-]?)(0|([1-9][0-9]*))(\\.[0-9]+)?$";
    private static final String FORCE_FORMAT = "%.2f";
    private static final PseudoClass ERROR = PseudoClass.getPseudoClass("error");

    private Simulation simul;

    @FXML private StackPane forcePanel;
    @FXML private TextField forceTextField;
    @FXML private Slider forceSlider;

    public void init(Simulation simul) {
        this.simul = simul;
        initializeControls();
        setupEventListeners();
        setupSimulationListeners();
    }

    private void initializeControls() {
        forceTextField.setDisable(true);
        forceSlider.setDisable(true);
        forceSlider.valueProperty().bindBidirectional(simul.getaForce().valueProperty());
    }

    private void setupEventListeners() {
        setupTextFieldValidation();
        setupSliderToTextFieldBinding();
        setupForceChangeListeners();
    }

    private void setupTextFieldValidation() {
        forceTextField.textProperty().addListener((obs, old, text) ->
                forceTextField.pseudoClassStateChanged(ERROR,
                        !text.isEmpty() && !text.matches(NUMBER_REGEX)));
    }

    private void setupSliderToTextFieldBinding() {
        forceSlider.valueProperty().addListener((obs, old, value) ->
                forceTextField.setText(String.format(FORCE_FORMAT, value)));
    }

    private void setupForceChangeListeners() {
        simul.getaForce().valueProperty().addListener(obs -> {
            updateFrictionForce();
            handleSimulationState();
            forceTextField.getParent().requestFocus();
        });
    }

    private void setupSimulationListeners() {
        simul.objProperty().addListener((obs, old, newObj) -> handleObjectChange(newObj));
        setupNetForceListeners();
    }

    private void handleObjectChange(Object newObj) {
        boolean hasObject = newObj != null;
        forceTextField.setDisable(!hasObject);
        forceSlider.setDisable(!hasObject);

        if (!hasObject) {
            resetForce();
        } else {
            setupObjectForces(newObj);
        }
    }

    private void resetForce() {
        forceTextField.setText("0");
        simul.setaForce(0);
    }

    private void setupObjectForces(Object newObj) {
        ((FrictionForce) simul.getfForce()).setMainObj(newObj);
        simul.getObj().velProperty().valueProperty()
                .addListener((obs, old, val) -> updateFrictionForce());
    }

    private void updateFrictionForce() {
        try {
            ((FrictionForce) simul.getfForce()).updateFrictionForce();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleSimulationState() {
        double force = simul.getaForce().getValue();
        if (!simul.getIsStart() && force != 0.0) {
            simul.start();
        } else if (simul.getIsPause() && simul.getIsStart()) {
            simul.conti();
        }
    }

    @FXML
    void forceTextFieldOnAction(ActionEvent event) {
        try {
            double force = Double.parseDouble(forceTextField.getText());
            simul.getaForce().setValue(force);
            validateForceRange(force);
        } catch (Exception e) {
            showForceRangeAlert(e.getMessage());
        }
    }

    private void validateForceRange(double force) {
        if (Math.abs(force) > AppliedForce.ABS_MAX_AFORCE) {
            showForceRangeAlert(null);
        }