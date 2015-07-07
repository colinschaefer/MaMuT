package fiji.plugin.mamut.viewer;

import mpicbg.spim.data.generic.AbstractSpimData;
import bdv.cl.RenderSlice;
import bdv.cl.VolumeRenderer;
import bdv.tools.brightness.BrightnessDialog;
import bdv.tools.brightness.SetupAssignments;
import bdv.tools.zdim.ZdimDialog;
import bdv.viewer.InputActionBindings;
import bdv.viewer.ViewerPanel;

public class MamutVolumeRenderer extends VolumeRenderer {
	RenderSlice render;
	float currentdimZ = 20;
	float minBright = 0;
	float maxBright = 0;
	ViewerPanel renderViewer;
	SetupAssignments renderSetup;
	ZdimDialog renderZdim;
	BrightnessDialog renderBrightness;
	boolean retimed = false;

	public MamutVolumeRenderer(final AbstractSpimData<?> spimData,
			final MamutViewerPanel viewer, final ZdimDialog zdimDialog,
			final SetupAssignments setupAssignments,
			final InputActionBindings bindings,
			BrightnessDialog brightnessDialog) {
		super(spimData, viewer, zdimDialog, setupAssignments, bindings,
				brightnessDialog);
	}

}
