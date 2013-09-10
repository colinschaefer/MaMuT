package fiji.plugin.mamut.io;

import static fiji.plugin.trackmate.io.TmXmlKeys.GUI_STATE_ELEMENT_KEY;
import static fiji.plugin.trackmate.io.TmXmlKeys.GUI_VIEW_ATTRIBUTE;
import static fiji.plugin.trackmate.io.TmXmlKeys.GUI_VIEW_ATTRIBUTE_POSITION_HEIGHT;
import static fiji.plugin.trackmate.io.TmXmlKeys.GUI_VIEW_ATTRIBUTE_POSITION_WIDTH;
import static fiji.plugin.trackmate.io.TmXmlKeys.GUI_VIEW_ATTRIBUTE_POSITION_X;
import static fiji.plugin.trackmate.io.TmXmlKeys.GUI_VIEW_ATTRIBUTE_POSITION_Y;
import static fiji.plugin.trackmate.io.TmXmlKeys.GUI_VIEW_ELEMENT_KEY;
import static fiji.plugin.trackmate.io.TmXmlKeys.SETTINGS_ELEMENT_KEY;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;

import org.jdom2.Element;

import viewer.gui.brightness.SetupAssignments;
import fiji.plugin.mamut.SourceSettings;
import fiji.plugin.mamut.viewer.MamutViewer;
import fiji.plugin.trackmate.Settings;
import fiji.plugin.trackmate.gui.TrackMateGUIModel;
import fiji.plugin.trackmate.io.TmXmlWriter;
import fiji.plugin.trackmate.providers.DetectorProvider;
import fiji.plugin.trackmate.providers.TrackerProvider;
import fiji.plugin.trackmate.visualization.TrackMateModelView;
import fiji.plugin.trackmate.visualization.trackscheme.TrackScheme;

public class MamutXmlWriter extends TmXmlWriter {

	public MamutXmlWriter(final File file) {
		super(file);
	}

	/**
	 * Appends the content of a {@link Settings} object to the document.
	 * 
	 * @param settings
	 *            the {@link Settings} to write. It must be a
	 *            {@link SourceSettings} instance, otherwise an exception is
	 *            thrown.
	 * @param detectorProvider
	 *            the {@link DetectorProvider}, required to marshall the
	 *            selected detector and its settings. If <code>null</code>, they
	 *            won't be appended.
	 * @param trackerProvider
	 *            the {@link TrackerProvider}, required to marshall the selected
	 *            tracker and its settings. If <code>null</code>, they won't be
	 *            appended.
	 */
	@Override
	public void appendSettings(final Settings settings,
			final DetectorProvider detectorProvider,
			final TrackerProvider trackerProvider) {

		if (!(settings instanceof SourceSettings)) {
			throw new IllegalArgumentException(
					"The settings must be a SourceSettings instance.");
		}

		final SourceSettings ss = (SourceSettings) settings;

		final Element settingsElement = new Element(SETTINGS_ELEMENT_KEY);

		final Element imageInfoElement = echoImageInfo(ss);
		settingsElement.addContent(imageInfoElement);

		if (null != detectorProvider) {
			final Element detectorElement = echoDetectorSettings(settings,
					detectorProvider);
			settingsElement.addContent(detectorElement);
		}

		final Element initFilter = echoInitialSpotFilter(settings);
		settingsElement.addContent(initFilter);

		final Element spotFiltersElement = echoSpotFilters(settings);
		settingsElement.addContent(spotFiltersElement);

		if (null != trackerProvider) {
			final Element trackerElement = echoTrackerSettings(settings,
					trackerProvider);
			settingsElement.addContent(trackerElement);
		}

		final Element trackFiltersElement = echoTrackFilters(settings);
		settingsElement.addContent(trackFiltersElement);

		final Element analyzersElement = echoAnalyzers(settings);
		settingsElement.addContent(analyzersElement);

		root.addContent(settingsElement);
	}

	public void appendMamutState(final TrackMateGUIModel guimodel,
			final SetupAssignments setupAssignments) {
		final Element guiel = new Element(GUI_STATE_ELEMENT_KEY);
		// views
		for (final TrackMateModelView view : guimodel.getViews()) {
			final Element viewel = new Element(GUI_VIEW_ELEMENT_KEY);
			viewel.setAttribute(GUI_VIEW_ATTRIBUTE, view.getKey());
			guiel.addContent(viewel);

			if (view.getKey().equals(MamutViewer.KEY)) {
				final MamutViewer mv = (MamutViewer) view;
				final Point location = mv.getFrame().getLocation();
				final Dimension size = mv.getFrame().getSize();
				viewel.setAttribute(GUI_VIEW_ATTRIBUTE_POSITION_X, ""
						+ location.x);
				viewel.setAttribute(GUI_VIEW_ATTRIBUTE_POSITION_Y, ""
						+ location.y);
				viewel.setAttribute(GUI_VIEW_ATTRIBUTE_POSITION_WIDTH, ""
						+ size.width);
				viewel.setAttribute(GUI_VIEW_ATTRIBUTE_POSITION_HEIGHT, ""
						+ size.height);

			} else if (view.getKey().equals(TrackScheme.KEY)) {
				final TrackScheme ts = (TrackScheme) view;
				final Point location = ts.getGUI().getLocation();
				final Dimension size = ts.getGUI().getSize();
				viewel.setAttribute(GUI_VIEW_ATTRIBUTE_POSITION_X, ""
						+ location.x);
				viewel.setAttribute(GUI_VIEW_ATTRIBUTE_POSITION_Y, ""
						+ location.y);
				viewel.setAttribute(GUI_VIEW_ATTRIBUTE_POSITION_WIDTH, ""
						+ size.width);
				viewel.setAttribute(GUI_VIEW_ATTRIBUTE_POSITION_HEIGHT, ""
						+ size.height);
			}
		}
		final Element elem = setupAssignments.toXml();
		guiel.addContent(elem);

		root.addContent(guiel);
		logger.log("  Added GUI current state.\n");
	}
}
