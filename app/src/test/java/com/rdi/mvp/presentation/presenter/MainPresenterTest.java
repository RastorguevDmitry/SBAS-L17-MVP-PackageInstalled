package com.rdi.mvp.presentation.presenter;

import com.rdi.mvp.presentation.view.IPackageInstalledView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    private MainPresenter mMainPresenter;

    @Mock
    private IPackageInstalledView mMainActivity;

    @Before
    public void setUp() throws Exception {
     //   mMainPresenter = new MainPresenter();
    }

    @Test
    public void testLoadData() {
      //  when(mP)

        mMainPresenter.loadData();
        Mockito.verify(mMainActivity).showProgress();
    }
}