package fiji.plugin.mamut.viewer;

import mpicbg.spim.data.generic.AbstractSpimData;
import bdv.cl.VolumeRenderer;
import bdv.tools.brightness.BrightnessDialog;
import bdv.tools.brightness.SetupAssignments;
import bdv.tools.zdim.ZdimDialog;
import bdv.viewer.InputActionBindings;

public class MamutVolumeRenderer extends VolumeRenderer {

	public MamutVolumeRenderer(final AbstractSpimData<?> spimData,
			final MamutViewerPanel viewer, final ZdimDialog zdimDialog,
			final SetupAssignments setupAssignments,
			final InputActionBindings bindings,
			BrightnessDialog brightnessDialog) {

		// construction with the superclass constructor
		super(spimData, viewer, zdimDialog, setupAssignments, bindings,
				brightnessDialog);

		// initial rendering with setting of the right parameters
		viewer.inverseMaxproj();
		viewer.showMessage("maximum projection ON");
		super.render();
	}
}
