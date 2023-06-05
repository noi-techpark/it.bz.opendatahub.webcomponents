// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package it.bz.opendatahub.webcomponents.common.util;

import lombok.NonNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ThumbnailUtility {
	private ThumbnailUtility() {
	}

	public static BufferedImage createThumbnailForImage(@NonNull final InputStream image, final int maxSideLengthInPx) throws IOException {
		return createThumbnailForImage(image, maxSideLengthInPx, maxSideLengthInPx);
	}

	public static BufferedImage createThumbnailForImage(@NonNull final InputStream image, final int maxWidthInPx, final int maxHeightInPx) throws IOException {
		BufferedImage img = ImageIO.read(image);
		int newWidth = img.getWidth();
		int newHeight = img.getHeight();
		int oldNewHeight;
		if (newWidth > maxWidthInPx) {
			newWidth = maxWidthInPx;
			oldNewHeight = 100 * maxWidthInPx / img.getWidth();
			newHeight = newHeight * oldNewHeight / 100;
		}

		if (newHeight > maxHeightInPx) {
			oldNewHeight = newHeight;
			newHeight = maxHeightInPx;
			int scaleDownRatio = 100 * maxHeightInPx / oldNewHeight;
			newWidth = newWidth * scaleDownRatio / 100;
		}

		Image thumb = img.getScaledInstance(newWidth, newHeight, 4);
		return toBufferedImage(thumb);
	}

	public static ByteArrayOutputStream toPng(final BufferedImage image) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "PNG", outputStream);
		return outputStream;
	}

	public static ByteArrayOutputStream toJpg(final BufferedImage image) throws IOException {
		BufferedImage destinationImage = new BufferedImage(image.getWidth((ImageObserver)null), image.getHeight((ImageObserver)null), 1);
		Graphics2D bGr = destinationImage.createGraphics();
		bGr.drawImage(image, 0, 0, (ImageObserver)null);
		bGr.dispose();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(destinationImage, "JPG", outputStream);
		return outputStream;
	}

	private static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage)img;
		} else {
			BufferedImage bimage = new BufferedImage(img.getWidth((ImageObserver)null), img.getHeight((ImageObserver)null), 2);
			Graphics2D bGr = bimage.createGraphics();
			bGr.drawImage(img, 0, 0, (ImageObserver)null);
			bGr.dispose();
			return bimage;
		}
	}
}
