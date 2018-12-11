package kr.co.blockcom.common.util;

import kr.co.blockcom.board.vo.PagingVO;

public class PageMaker<T extends PagingVO> {

	public PageMaker(PagingVO pvo) {

	}

	public PagingVO setPaging(PagingVO pvo) {

		pvo.setTotalPage((int) (Math.ceil(pvo.getRealTotalCount() / (double) pvo.getPerPageNumber())));

		pvo.setTempEndPage((int) (Math.ceil(pvo.getPage() / (double) pvo.getBlockSize()) * pvo.getBlockSize()));
		pvo.setStartPage((pvo.getTempEndPage() - pvo.getBlockSize()) + 1);
		if (pvo.getStartPage() < 1)
			pvo.setStartPage(1);
		if (pvo.getTempEndPage() > pvo.getTotalPage())
			pvo.setTempEndPage(pvo.getTotalPage());

		pvo.setListStart((pvo.getPage() - 1) * pvo.getPerPageNumber());

		if (pvo.getPage() != 1)
			pvo.setPrev(true);

		if (pvo.getPage() < pvo.getTotalPage())
			pvo.setNext(true);

		return pvo;
	}

}
